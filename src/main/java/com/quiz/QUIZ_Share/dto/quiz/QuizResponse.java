package com.quiz.QUIZ_Share.dto.quiz;

import com.quiz.QUIZ_Share.dto.question.QuestionResponse;
import com.quiz.QUIZ_Share.enums.Difficulty;
import com.quiz.QUIZ_Share.enums.Privacy;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
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
    private String firstName;
    private String lastName;
    private List<Integer> rate;
    private String imageUrl;
    private Integer takeTimeLimit;
}
