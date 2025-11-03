package com.quiz.QUIZ_Share.dto;

import com.quiz.QUIZ_Share.entity.Questions;
import com.quiz.QUIZ_Share.entity.User;
import com.quiz.QUIZ_Share.enums.Difficulty;
import com.quiz.QUIZ_Share.enums.Privacy;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuizRequest {

    @NotNull
    private String title;

    @NotNull
    private Difficulty difficulty;

    @NotNull
    private String subject;
    private String description;

    @NotNull
    private Privacy privacy;

    @NotNull
    private Set<QuestionRequest> question;

    @NotNull
    private Integer authorId;

}
