package com.quiz.QUIZ_Share.dto.auth;

import com.quiz.QUIZ_Share.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String firstName;
    private String lastName;

    private String email;
    private String username;
    private String password;
    private String confirmPassword;
    private Role role;
}
