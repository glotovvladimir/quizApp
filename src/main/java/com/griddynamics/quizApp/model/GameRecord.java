package com.griddynamics.quizApp.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class GameRecord {
    
    String name;
    double score;
}
