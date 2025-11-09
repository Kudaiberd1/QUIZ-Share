package com.quiz.QUIZ_Share.controller;

import com.quiz.QUIZ_Share.dto.auth.AuthenticationResponse;
import com.quiz.QUIZ_Share.dto.auth.LoginRequest;
import com.quiz.QUIZ_Share.dto.auth.RegisterRequest;
import com.quiz.QUIZ_Share.dto.auth.UserReponse;
import com.quiz.QUIZ_Share.entity.User;
import com.quiz.QUIZ_Share.mappers.UserMapper;
import com.quiz.QUIZ_Share.repositories.UserRepository;
import com.quiz.QUIZ_Share.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody LoginRequest request
    ) {
        return ResponseEntity.ok(authenticationService.login(request));
    }

    @GetMapping("/id")
    public ResponseEntity<UserReponse> getById(@RequestParam Integer id) {
        return ResponseEntity.ok(userMapper.toDto(userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("user not found by this id"))));
    }
}
