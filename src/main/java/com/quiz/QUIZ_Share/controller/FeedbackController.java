package com.quiz.QUIZ_Share.controller;

import com.quiz.QUIZ_Share.dto.feedback.FeedbackCreatRequest;
import com.quiz.QUIZ_Share.dto.feedback.FeedbackResponse;
import com.quiz.QUIZ_Share.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/quiz/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;

    @GetMapping("/{id}")
    public ResponseEntity<List<FeedbackResponse>> getAllFeedbackById(@PathVariable Long id){
        List<FeedbackResponse> feedbacks = feedbackService.getByQuizId(id);

        return ResponseEntity.ok(feedbacks);
    }

    @GetMapping()
    public ResponseEntity<FeedbackResponse> feedback(@RequestParam Integer quizId, @RequestParam Integer authorId ) {
        FeedbackResponse feedbackResponse = feedbackService.getFeedback(authorId, quizId);
        return ResponseEntity.ok(feedbackResponse);
    }


    @PostMapping()
    public ResponseEntity<FeedbackResponse> feedbackCreate(@RequestBody FeedbackCreatRequest feedback) {
        FeedbackResponse newFeedback = feedbackService.createFeedback(feedback);
        return ResponseEntity.ok(newFeedback);
    }

}
