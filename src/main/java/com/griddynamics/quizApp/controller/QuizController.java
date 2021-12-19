package com.griddynamics.quizApp.controller;

import com.griddynamics.quizApp.dataHolder.DataHolder;
import com.griddynamics.quizApp.model.AnswersData;
import com.griddynamics.quizApp.model.PlayerData;
import com.griddynamics.quizApp.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;

@Controller
public class QuizController {
    
    final private String appName;
    final private QuizService quizService;
    final private DataHolder dh;

    @Autowired
    public QuizController(QuizService quizService, DataHolder dh, @Value("${spring.application.name}") String appName) {
        this.quizService = quizService;
        this.dh = dh;
        this.appName = appName;
    }

    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("appName", appName);
        model.addAttribute("info", new PlayerData());
        return "home";
    }
    
    @PostMapping(path = "/",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String questionsPage(@ModelAttribute("amount") PlayerData info, Model model) {
        dh.setPlayerName(info.getName());
        quizService.generateQuestionListWithParam(info.getAmount());
        model.addAttribute("questions", dh.getQuestionsInUse());
        model.addAttribute("answersData", new AnswersData(new HashMap<>(), Integer.parseInt(info.getAmount())));
        return "questionsPage";
    }
    
    @PostMapping(path = "/questions", 
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String resultsPage(@RequestBody MultiValueMap<String, String> answersData, Model model) {
        model.addAttribute("score", quizService.calculateCorrectAnswers(answersData));
        quizService.saveDataToDb(dh.getPlayerName());
        model.addAttribute("name", dh.getPlayerName());
        model.addAttribute("top5", quizService.get5TopGames());
        return "resultsPage";
    }

    @GetMapping("/sessionData")
    public String getSessionData(Model model) {
        model.addAttribute("currentSession", dh.toString());
        return "sessionData";
    }
}
