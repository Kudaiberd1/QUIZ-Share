package com.quiz.QUIZ_Share.dto;

import com.quiz.QUIZ_Share.enums.Answer;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionRequest {
    private String question;

    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;

    private Answer answer;
}
