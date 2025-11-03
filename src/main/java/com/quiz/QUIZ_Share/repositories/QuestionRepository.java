package com.quiz.QUIZ_Share.repositories;

import com.quiz.QUIZ_Share.entity.Questions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Questions, Integer> {
}
