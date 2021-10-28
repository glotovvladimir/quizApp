package com.griddynamics.quizApp.model;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class AnswersData {

    Map<String, String> answerList = new HashMap<>();
    
    public AnswersData(Map<String, String> answers, int i) {
       this.answerList = answers;
       
        for (int j = 0; j < i; j++) {
            answers.put(String.valueOf(j), null);
        }
    }
}
