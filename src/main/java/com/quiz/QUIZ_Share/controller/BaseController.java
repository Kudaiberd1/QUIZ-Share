package com.quiz.QUIZ_Share.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping()
public class BaseController {

    @GetMapping()
    public String index() {
        log.info("Connected to the API");
        return "index";
    }
}
