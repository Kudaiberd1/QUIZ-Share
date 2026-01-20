package com.quiz.quizshare.dto.question;

import com.quiz.quizshare.dto.variant.VariantRequest;
import com.quiz.quizshare.entity.Variant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionUpdateRequest {

    private Integer id;

    private String question;

    private List<Variant> variants;

    private List<VariantRequest> newVariants;

    private List<Integer> answer;
}
