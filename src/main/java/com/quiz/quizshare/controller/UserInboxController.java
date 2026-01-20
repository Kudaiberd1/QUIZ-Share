package com.quiz.quizshare.controller;

import com.quiz.quizshare.dto.feedback.FeedbackResponse;
import com.quiz.quizshare.service.UserInboxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user/inbox")
public class UserInboxController {

    private final UserInboxService userInboxService;

    @GetMapping()
    public ResponseEntity<List<FeedbackResponse>> getAllUserInbox(@AuthenticationPrincipal Jwt jwt){
        String username = jwt.getClaim("preferred_username");

        List<FeedbackResponse> feedbacks = userInboxService.getFeedbacks(username);

        return ResponseEntity.ok(feedbacks);
    }

    @PostMapping("/{id}")
    public void markAsRead(@PathVariable Integer id){
        userInboxService.markAsRead(id);
    }
}
