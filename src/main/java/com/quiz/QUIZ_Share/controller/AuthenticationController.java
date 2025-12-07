package com.quiz.QUIZ_Share.controller;

import com.quiz.QUIZ_Share.dto.auth.AuthenticationResponse;
import com.quiz.QUIZ_Share.dto.auth.LoginRequest;
import com.quiz.QUIZ_Share.dto.auth.RegisterRequest;
import com.quiz.QUIZ_Share.dto.auth.UserReponse;
import com.quiz.QUIZ_Share.mappers.UserMapper;
import com.quiz.QUIZ_Share.repositories.UserRepository;
import com.quiz.QUIZ_Share.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> register(
            @RequestPart("data") RegisterRequest request,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) {
        authenticationService.register(request, file);
        return ResponseEntity.ok(Map.of("message", "User registered successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestBody LoginRequest request
    ) {
        return ResponseEntity.ok(authenticationService.login(request));
    }

    @GetMapping("/id")
    public ResponseEntity<UserReponse> getById(@RequestParam Integer id) {
        return ResponseEntity.ok(userMapper.toDto(userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("user not found by this id"))));
    }

    @GetMapping("/me")
    public ResponseEntity<UserReponse> getCurrentUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var email = authentication.getName();


        return ResponseEntity.ok(userMapper.toDto(userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("Authorization not found"))));
    }
}
