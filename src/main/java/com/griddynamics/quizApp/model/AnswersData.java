package com.griddynamics.quizApp.model;

import lombok.Data;

import java.util.Map;

@Data
public class AnswersData {

    Map<Integer, String> answerList;

    public AnswersData(Map<Integer, String> answers, int i) {
       this.answerList = answers;
       
        for (int j = 0; j < i; j++) {
            answers.put(j, "");
        }
    }
}
