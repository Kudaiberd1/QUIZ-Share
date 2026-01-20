package com.quiz.quizshare.dto.quiz;

import com.quiz.quizshare.dto.question.QuestionRequest;
import com.quiz.quizshare.dto.question.QuestionUpdateRequest;
import com.quiz.quizshare.enums.Difficulty;
import com.quiz.quizshare.enums.Privacy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuizUpdateRequest {

    private String title;
    private Difficulty difficulty;
    private String subject;
    private String description;
    private Privacy privacy;
    private Integer rate;
    private Set<QuestionRequest> newQuestion;
    private Set<QuestionUpdateRequest> questions;
    private Long authorId;
    private MultipartFile file;
}
