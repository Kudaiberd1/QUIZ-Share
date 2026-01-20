package com.quiz.quizshare.controller;

import com.quiz.quizshare.dto.takenQuiz.TakenQuizCreateRequest;
import com.quiz.quizshare.dto.takenQuiz.TakenQuizResponse;
import com.quiz.quizshare.service.TakenQuizService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/user/{id}")
    public ResponseEntity<List<TakenQuizResponse>> getAllTakenQuiz(@PathVariable Integer id){
        List<TakenQuizResponse> listTakenQuizzes = takenQuizService.getTakenQuizzesByUser(id);
        return ResponseEntity.ok(listTakenQuizzes);
    }

    @PostMapping()
    public ResponseEntity<TakenQuizResponse> createTakenQuiz(@RequestBody TakenQuizCreateRequest result){
        TakenQuizResponse createdResult = takenQuizService.createResult(result);
        return ResponseEntity.ok(createdResult);
    }
}
