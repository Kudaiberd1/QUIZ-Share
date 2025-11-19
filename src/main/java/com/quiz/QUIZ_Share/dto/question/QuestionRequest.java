package com.quiz.QUIZ_Share.dto.question;

import com.quiz.QUIZ_Share.dto.variant.VariantRequest;
import com.quiz.QUIZ_Share.entity.Variant;
import com.quiz.QUIZ_Share.enums.Answer;
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
