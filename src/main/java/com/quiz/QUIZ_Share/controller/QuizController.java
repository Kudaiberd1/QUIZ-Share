package com.quiz.QUIZ_Share.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.quiz.QUIZ_Share.dto.QuizRequest;
import com.quiz.QUIZ_Share.dto.QuizResponse;
import com.quiz.QUIZ_Share.entity.Quiz;
import com.quiz.QUIZ_Share.service.QuizService;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/quiz")
public class QuizController {

    private final QuizService quizService;

    @GetMapping()
    public ResponseEntity<List<QuizResponse>> getAllQuizzes(){
        return ResponseEntity.ok(quizService.getAll());
    }

    @GetMapping("/id")
    public ResponseEntity<QuizResponse> getQuizById(@PathVariable Long id){
        return ResponseEntity.ok(quizService.getById(id));
    }

    @PostMapping()
    public ResponseEntity<QuizResponse> createQuiz(
            @RequestBody @Valid QuizRequest quizRequest
    ){

        QuizResponse quizResponse = quizService.createQuiz(quizRequest);
        return ResponseEntity.ok(quizResponse);
    }

}
