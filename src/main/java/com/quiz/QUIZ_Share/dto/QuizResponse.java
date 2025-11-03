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

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizResponse {

    private Integer id;
    private String title;
    private Difficulty difficulty;
    private String subject;
    private String description;
    private Privacy privacy;
    private Set<QuestionResponse> questions;
    private Integer userId;
}
