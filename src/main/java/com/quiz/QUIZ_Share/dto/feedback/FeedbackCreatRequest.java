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
    private Long quizId;
    private String feedback;
    private Integer star;

    @Override
    public String toString() {
        return "FeedbackCreatRequest{" +
                ", quizId=" + quizId +
                ", feedback='" + feedback + '\'' +
                ", star=" + star +
                '}';
    }
}
