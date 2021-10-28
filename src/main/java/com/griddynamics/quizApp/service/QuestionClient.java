package com.griddynamics.quizApp.service;

import feign.QueryMap;
import feign.RequestLine;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.Map;

@FeignClient(name = "questionClient")
public interface QuestionClient {

    @RequestLine("GET")
    Response findWithAmount(@QueryMap Map<String, String> parameters);
}
