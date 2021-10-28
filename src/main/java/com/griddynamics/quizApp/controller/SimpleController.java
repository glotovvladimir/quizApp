package com.griddynamics.quizApp.controller;

import com.griddynamics.quizApp.model.AnswersData;
import com.griddynamics.quizApp.model.PlayerData;
import com.griddynamics.quizApp.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;

@Controller
public class SimpleController {

    @Value("${spring.application.name}")
    String appName;
    
    @Autowired
    private QuizService quizService;

    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("appName", appName);
        model.addAttribute("amount", new PlayerData());
        return "home";
    }
    
    @PostMapping("/")
    public String questionsPage(@ModelAttribute("amount") String amount, Model model) {
        model.addAttribute("questions", quizService.getQuestionListWithParameter(amount));
        model.addAttribute("answersData", new AnswersData(new HashMap<>(), Integer.parseInt(amount)));
        return "questionsPage";
    }

    @PostMapping("/questions")
    public String resultsPage(@ModelAttribute("answersData") AnswersData answersData, Model model) {
        model.addAttribute("answersData", answersData);
        return "resultsPage";
    }
}
