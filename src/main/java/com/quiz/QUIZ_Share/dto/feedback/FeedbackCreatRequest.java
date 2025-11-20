package com.quiz.QUIZ_Share.dto.feedback;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackCreatRequest {
    @NotNull
    private String feedback;
    @NotNull
    private Integer star;
    @NotNull
    private Long authorId;
    @NotNull
    private Long quizId;
}
