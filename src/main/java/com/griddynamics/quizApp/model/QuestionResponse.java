package com.griddynamics.quizApp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;

@Data
public class QuestionResponse {
    @JsonProperty("response_code")
    private int response_code;
    @JsonProperty("results")
    private ArrayList<Question> results;
}
