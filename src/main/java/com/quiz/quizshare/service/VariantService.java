package com.quiz.quizshare.service;

import com.quiz.quizshare.dto.variant.VariantRequest;
import com.quiz.quizshare.entity.Questions;
import com.quiz.quizshare.entity.Variant;
import com.quiz.quizshare.repositories.VariantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class VariantService {

    private final VariantRepository variantRepository;

    public List<Variant> createVariant(List<VariantRequest> variant, Questions question) {
        log.info("Create variant {}", variant);

        List<Variant> newVariants = new ArrayList<>();

        for (VariantRequest varinant : variant) {
            Variant newVariant = new Variant();

            newVariant.setOption(varinant.getOption());
            newVariant.setQuestion(question);

            var savedVariant = variantRepository.save(newVariant);
            newVariants.add(savedVariant);
        }

        return newVariants;
    }
}
