package com.quiz.quizshare.dto.feedback;

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
    private Long toUserId;
    private Long authorId;
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
