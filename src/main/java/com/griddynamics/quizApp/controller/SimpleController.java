package com.griddynamics.quizApp.controller;

import com.griddynamics.quizApp.model.AnswersData;
import com.griddynamics.quizApp.model.PlayerData;
import com.griddynamics.quizApp.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;

@Controller
public class SimpleController {

    @Value("${spring.application.name}")
    String appName;
    
    @Autowired
    private QuizService quizService;
    private String playerName;

    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("appName", appName);
        model.addAttribute("info", new PlayerData());
        return "home";
    }
    
    @PostMapping("/")
    public String questionsPage(@ModelAttribute("amount") PlayerData info, Model model) {
        model.addAttribute("questions", quizService.getQuestionListWithParameter(info.getAmount()));
        model.addAttribute("answersData", new AnswersData(new HashMap<>(), Integer.parseInt(info.getAmount())));
        playerName = info.getName();
        return "questionsPage";
    }
    
    @PostMapping("/questions")
    public String resultsPage(@RequestBody MultiValueMap<String, String> answersData, Model model) {
        model.addAttribute("score", quizService.calculateCorrectAnswers(answersData));
        model.addAttribute("name", playerName);
        return "resultsPage";
    }
}
