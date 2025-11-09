package com.quiz.QUIZ_Share.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserReponse {

    private Integer id;
    private String fristName;
    private String lastName;
    private String email;
}
