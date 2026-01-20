package com.quiz.quizshare.repositories;

import com.quiz.quizshare.entity.Variant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VariantRepository extends JpaRepository<Variant,Long> {
}
