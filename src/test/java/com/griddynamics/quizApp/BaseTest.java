package com.griddynamics.quizApp;

import com.griddynamics.quizApp.service.QuizService;

public class BaseTest {
    
    public QuizService getQuizService() {
        return new QuizService();
    }
}
