package com.quiz.quizshare.controller;

import com.quiz.quizshare.dto.quiz.QuizCreateRequest;
import com.quiz.quizshare.dto.quiz.QuizResponse;
import com.quiz.quizshare.dto.quiz.QuizUpdateRequest;
import com.quiz.quizshare.enums.Filter;
import com.quiz.quizshare.service.QuizService;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.service.GenericResponseService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<QuizResponse> createQuiz(
            @RequestPart("data") QuizCreateRequest quizCreateRequest,
            @RequestPart(value = "file", required = false ) MultipartFile file
    ){
        QuizResponse quizResponse = quizService.createQuiz(quizCreateRequest, file);
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
    public void deleteQuiz(@PathVariable Long id){
        quizService.deleteQuiz(id);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<QuizResponse>> getAllQuizzesByFilter(
            @RequestParam(required = false) String text,
            @RequestParam(required = false) String subject,
            @RequestParam(required = false) Filter filter
            ){
        var res = quizService.filterQuiz(text, subject, filter);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/subjects")
    public ResponseEntity<List<String>> getAllSubject(){
        return ResponseEntity.ok(quizService.getAllSubject());
    }
}
