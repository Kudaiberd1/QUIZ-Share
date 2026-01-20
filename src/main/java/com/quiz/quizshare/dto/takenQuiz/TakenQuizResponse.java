package com.quiz.quizshare.dto.takenQuiz;

import com.quiz.quizshare.dto.answer.AnswerDto;
import com.quiz.quizshare.dto.quiz.QuizResponse;
import com.quiz.quizshare.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TakenQuizResponse {
    private Long id;
    private QuizResponse quiz;
    private List<AnswerDto> userAnswers;
    private List<Status> statuses;
    private Integer correct;
    private Integer wrong;
    private Integer skipped;
    private double rating;
    private Integer authorId;
    private Integer quizId;
}
