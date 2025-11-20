package com.quiz.QUIZ_Share.dto.auth;

import com.quiz.QUIZ_Share.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserReponse {

    private Integer id;
    private String firstName;
    private String lastName;
    private String imageUrl;
    private String email;
    private Role role;
    private List<Integer> results;
}
