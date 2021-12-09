package com.griddynamics.quizApp.dataHolder;

import com.griddynamics.quizApp.model.Question;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.Arrays;

@Component
@SessionScope
@Data
public class DataHolder {

    private ArrayList<Question> questionsInUse;
    private double[] currentResults;
    private String playerName;

    @Override
    public String toString() {
        return "DataHolder{" +
                "questionsInUse=" +
                ", currentResults=" + Arrays.toString(currentResults) +
                ", playerName='" + playerName + '\'' +
                '}';
    }
}
