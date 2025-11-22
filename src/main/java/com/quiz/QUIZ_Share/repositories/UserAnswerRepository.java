package com.quiz.QUIZ_Share.repositories;

import com.quiz.QUIZ_Share.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAnswerRepository extends JpaRepository<Answer, Long> {
}
