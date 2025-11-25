package com.quiz.QUIZ_Share.dto.feedback;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackResponse {
    private Integer id;
    private String feedback;
    private Integer star;
    private Integer authorId;
    private Integer quizId;
    private boolean isReaded;
    private LocalDateTime createdAt;
}
