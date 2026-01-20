package com.quiz.quizshare.dto.auth;

import com.quiz.quizshare.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private Integer id;
    private String username;
    private String firstName;
    private String lastName;
    private String imageUrl;
    private String email;
    private Role role;
    private List<Integer> results;
}
