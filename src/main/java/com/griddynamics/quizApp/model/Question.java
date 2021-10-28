package com.griddynamics.quizApp.model;

import lombok.Data;

@Data
public class Question {
    
    private String category;
    private String type;
    private String difficulty;
    private String question;
    private String correct_answer;
    private String[] incorrect_answers;
}
