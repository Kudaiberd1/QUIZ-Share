package com.quiz.QUIZ_Share.controller;

import com.quiz.QUIZ_Share.dto.feedback.FeedbackResponse;
import com.quiz.QUIZ_Share.entity.User;
import com.quiz.QUIZ_Share.mappers.FeedbackMapper;
import com.quiz.QUIZ_Share.repositories.FeedbackRepository;
import com.quiz.QUIZ_Share.repositories.UserRepository;
import com.quiz.QUIZ_Share.service.UserInboxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
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
