package com.quiz.QUIZ_Share.controller;

import com.quiz.QUIZ_Share.dto.auth.LoginRequest;
import com.quiz.QUIZ_Share.dto.auth.RegisterRequest;
import com.quiz.QUIZ_Share.dto.auth.UserResponse;
import com.quiz.QUIZ_Share.dto.keycloak.AuthResponse;
import com.quiz.QUIZ_Share.entity.User;
import com.quiz.QUIZ_Share.mappers.UserMapper;
import com.quiz.QUIZ_Share.repositories.UserRepository;
import com.quiz.QUIZ_Share.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
    public ResponseEntity<Map<String, String>> register(
            @RequestPart("data") RegisterRequest request,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) {
        authenticationService.registerUser(request, file);

        return ResponseEntity.ok(Map.of("message", "User registered successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody LoginRequest request
    ) throws BadRequestException {
        return ResponseEntity.ok(authenticationService.getAuthResponse(request.getEmail(), request.getPassword()));
    }

    @GetMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestParam("token") String token) throws BadRequestException {
        return ResponseEntity.ok(authenticationService.refreshToken(token));
    }

    @GetMapping("/id")
    public ResponseEntity<UserResponse> getById(@RequestParam Integer id) {
        return ResponseEntity.ok(userMapper.toDto(userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("user not found by this id"))));
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> me(@AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getClaimAsString("preferred_username");

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found with username: " + username));

        return ResponseEntity.ok(userMapper.toDto(user));
    }

}
