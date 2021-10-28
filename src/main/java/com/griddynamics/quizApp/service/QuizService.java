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
import org.springframework.util.StreamUtils;

import java.nio.charset.Charset;
import java.util.*;

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
}
