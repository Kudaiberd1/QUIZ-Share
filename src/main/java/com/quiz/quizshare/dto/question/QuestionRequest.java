package com.quiz.quizshare.dto.question;

import com.quiz.quizshare.dto.variant.VariantRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionRequest {
    private String question;

    private List<VariantRequest> variants;

    private List<Integer> answer;
}
