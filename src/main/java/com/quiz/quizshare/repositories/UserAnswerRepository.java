package com.quiz.quizshare.repositories;

import com.quiz.quizshare.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAnswerRepository extends JpaRepository<Answer, Long> {
}
