package com.quiz.QUIZ_Share.dto.auth;

import com.quiz.QUIZ_Share.enums.Role;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String firstName;
    private String lastName;

    private String email;
    private String password;
    private String confirmPassword;
    private Role role;
}
