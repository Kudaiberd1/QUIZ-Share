package com.quiz.QUIZ_Share.dto.takenQuiz;

import com.quiz.QUIZ_Share.dto.answer.AnswerDto;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TakenQuizCreateRequest {
    @NotNull
    private Long quiz;
    @NotNull
    private List<AnswerDto> userAnswers;
    @NotNull
    private Integer correct;
    @NotNull
    private Integer wrong;
    @NotNull
    private Integer skipped;
    @NotNull
    private Integer authorId;
}
