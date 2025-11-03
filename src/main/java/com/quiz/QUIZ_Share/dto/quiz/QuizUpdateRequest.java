package com.quiz.QUIZ_Share.dto.quiz;

import com.quiz.QUIZ_Share.dto.question.QuestionRequest;
import com.quiz.QUIZ_Share.enums.Difficulty;
import com.quiz.QUIZ_Share.enums.Privacy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuizUpdateRequest {

    @NotNull
    private String title;

    @NotNull
    private Difficulty difficulty;

    @NotNull
    private String subject;
    private String description;

    @NotNull
    private Privacy privacy;

    private Set<QuestionRequest> newQuestion;

    private Set<Integer> questionIds;

    @NotNull
    private Integer authorId;

}
