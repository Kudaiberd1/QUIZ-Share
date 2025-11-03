package com.quiz.QUIZ_Share.controller;

import com.quiz.QUIZ_Share.dto.quiz.QuizCreateRequest;
import com.quiz.QUIZ_Share.dto.quiz.QuizResponse;
import com.quiz.QUIZ_Share.dto.quiz.QuizUpdateRequest;
import com.quiz.QUIZ_Share.service.QuizService;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.service.GenericResponseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/quiz")
public class QuizController {

    private final QuizService quizService;
    private final GenericResponseService responseBuilder;

    @GetMapping()
    public ResponseEntity<List<QuizResponse>> getAllQuizzes(){
        return ResponseEntity.ok(quizService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuizResponse> getQuizById(@PathVariable Long id){
        return ResponseEntity.ok(quizService.getById(id));
    }

    @PostMapping()
    public ResponseEntity<QuizResponse> createQuiz(
            @RequestBody @Valid QuizCreateRequest quizCreateRequest
    ){

        QuizResponse quizResponse = quizService.createQuiz(quizCreateRequest);
        return ResponseEntity.ok(quizResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuizResponse> updateQuiz(
            @PathVariable Long id,
            @RequestBody @Valid QuizUpdateRequest quizUpdateRequest
    ){
        QuizResponse quizResponse = quizService.updateQuiz(id, quizUpdateRequest);
        return ResponseEntity.ok(quizResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteQuiz(@PathVariable Long id){
        quizService.deleteQuiz(id);
        return ResponseEntity.ok().build();
    }

}
