package com.quiz.QUIZ_Share.repositories;

import com.quiz.QUIZ_Share.entity.TakenQuiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TakenQuizRepository extends JpaRepository<TakenQuiz,Long> {
}
