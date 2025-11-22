package com.quiz.QUIZ_Share.dto.question;

import com.quiz.QUIZ_Share.entity.Variant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionResponse {
    private Long id;
    private String question;
    private List<Variant> variants;
    private List<Integer> answer;
}