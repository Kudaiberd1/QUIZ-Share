package com.quiz.quizshare.dto.quiz;

import com.quiz.quizshare.dto.question.QuestionResponse;
import com.quiz.quizshare.enums.Difficulty;
import com.quiz.quizshare.enums.Privacy;
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
