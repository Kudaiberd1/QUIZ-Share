package com.quiz.QUIZ_Share.service;

import com.quiz.QUIZ_Share.dto.variant.VariantRequest;
import com.quiz.QUIZ_Share.entity.Variant;
import com.quiz.QUIZ_Share.repositories.VariantRepository;
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

    public List<Variant> createVariant(List<VariantRequest> variant) {
        log.info("Create variant {}", variant);
        List<Variant> newVariants = new ArrayList<>();
        for (VariantRequest varinant : variant) {
            Variant newVariant = new Variant();
            newVariant.setOption(varinant.getOption());
            var savedVariant = variantRepository.save(newVariant);
            newVariants.add(savedVariant);
        }
        return newVariants;
    }
}
