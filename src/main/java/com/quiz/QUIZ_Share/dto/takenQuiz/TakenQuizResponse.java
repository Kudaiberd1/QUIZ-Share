package com.quiz.QUIZ_Share.dto.takenQuiz;

import com.quiz.QUIZ_Share.dto.answer.AnswerDto;
import com.quiz.QUIZ_Share.dto.question.QuestionResponse;
import com.quiz.QUIZ_Share.dto.quiz.QuizResponse;
import com.quiz.QUIZ_Share.enums.Status;
import jakarta.validation.constraints.NotNull;
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
