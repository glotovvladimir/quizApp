package com.griddynamics.quizApp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.griddynamics.quizApp.model.Question;
import com.griddynamics.quizApp.model.QuestionResponse;
import feign.Feign;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StreamUtils;

import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@Component
public class QuizService {
    
    public ArrayList<Question> questionsInUse;
    
    private final String URL = "https://opentdb.com/api.php";
    
    @SneakyThrows
    public ArrayList<Question> getQuestionListWithParameter(String amount) {
        String responseAsString;
        QuestionResponse questionResponse;
        ObjectMapper mapper = new ObjectMapper();
        QuestionClient questionClient = getQuestionClient(URL);
        Map<String, String> parameters = new LinkedHashMap<>();
        ArrayList<Question> questions = new ArrayList<>();

        parameters.put("amount", amount);
        responseAsString = StreamUtils
                .copyToString(questionClient.findWithAmount(parameters).body().asInputStream(), Charset.defaultCharset());
        questionResponse = mapper.readValue(responseAsString, QuestionResponse.class);
        questionsInUse = reformatQuestions(questionResponse.getResults());
        addCorrectAnswerToIncorrectOnes();
        return questionsInUse;
    }
    
    private QuestionClient getQuestionClient(String url) {
        return Feign.builder()
                .client(new OkHttpClient())
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .target(QuestionClient.class, url);
    }
    
    private ArrayList<Question> reformatQuestions(ArrayList<Question> list) {
        for (Question question: list) {
            question.setQuestion(question.getQuestion()
                    .replace("&#039;", "'")
                    .replace("&quot;", "\'")
                    .replace("&auml;", "ä"));
            question.setCorrect_answer(question.getCorrect_answer()
                    .replace("&#039;", "'")
                    .replace("&quot;", "\'")
                    .replace("&auml;", "ä"));
            for (String answer: question.getIncorrect_answers()) {
                answer
                        .replace("&#039;", "'")
                        .replace("&quot;", "\'")
                        .replace("&auml;", "ä");
            }
        }
        return list;
    }
    
    public void addCorrectAnswerToIncorrectOnes() {
        for (Question question : this.questionsInUse) {
            ArrayList<String> list = new ArrayList<>();
            list.add(question.getCorrect_answer());
            list.addAll(Arrays.asList(question.getIncorrect_answers()));
            Collections.shuffle(list);
            question.setIncorrect_answers(list.toArray(new String[0]));
        }
    }

    public ArrayList<String> extractAnswers(MultiValueMap<String, String> answersData) {
        ArrayList<String> result = new ArrayList<>();

        for (Map.Entry<String, List<String>> answer: answersData.entrySet()) {
            result.add(answer.getValue().get(0));
        }
        return result;
    }

    public double[] calculateCorrectAnswers(MultiValueMap<String, String> answersData) {
        double i = 0;
        double[] results = new double[3];
        ArrayList<String> givenAnswersList = extractAnswers(answersData);
        List<String> correctAnswersList = questionsInUse.stream().map(q -> q.getCorrect_answer()).collect(Collectors.toList());

        for (int j = 0; j < givenAnswersList.size() - 1; j++) {
            if (givenAnswersList.get(j).equals(correctAnswersList.get(j))) i++;
        }
        results[0] = i;
        results[1] = givenAnswersList.size();
        results[2] = i / results[1];
        return results;
    }
}
