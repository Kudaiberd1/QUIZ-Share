package com.quiz.QUIZ_Share.controller;

import com.quiz.QUIZ_Share.dto.takenQuiz.TakenQuizCreateRequest;
import com.quiz.QUIZ_Share.dto.takenQuiz.TakenQuizResponse;
import com.quiz.QUIZ_Share.entity.TakenQuiz;
import com.quiz.QUIZ_Share.service.TakenQuizService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/quiz/result")
public class TakenQuizController {

    private final TakenQuizService takenQuizService;

    @GetMapping("/{id}")
    public ResponseEntity<TakenQuizResponse> getTakenQuiz(@PathVariable Long id){
        TakenQuizResponse takenQuizResponse = takenQuizService.getResult(id);
        return ResponseEntity.ok(takenQuizResponse);
    }

    @PostMapping()
    public ResponseEntity<TakenQuizResponse> createTakenQuiz(@RequestBody TakenQuizCreateRequest result){
        TakenQuizResponse createdResult = takenQuizService.createResult(result);
        return ResponseEntity.ok(createdResult);
    }
}
