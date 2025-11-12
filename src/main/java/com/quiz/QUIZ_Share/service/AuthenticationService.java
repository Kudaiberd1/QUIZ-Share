package com.quiz.QUIZ_Share.service;

import com.quiz.QUIZ_Share.config.JwtService;
import com.quiz.QUIZ_Share.dto.auth.AuthenticationResponse;
import com.quiz.QUIZ_Share.dto.auth.LoginRequest;
import com.quiz.QUIZ_Share.dto.auth.RegisterRequest;
import com.quiz.QUIZ_Share.entity.User;
import com.quiz.QUIZ_Share.exceptions.GlobalExceptionHandler;
import com.quiz.QUIZ_Share.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;


    public AuthenticationResponse register(RegisterRequest request) {

        var user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .confirmPassword(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .role(request.getRole())
                .build();

        if(request.getPassword().equals(request.getConfirmPassword())) {
            userRepository.save(user);
        }else{
            throw new GlobalExceptionHandler.PasswordDonTMatchException("Passwords do not match");
        }
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}
