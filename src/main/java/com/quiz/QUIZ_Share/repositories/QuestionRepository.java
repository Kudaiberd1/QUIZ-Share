package com.quiz.QUIZ_Share.repositories;

import com.quiz.QUIZ_Share.entity.Questions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Questions, Integer> {
    @Query("SELECT q FROM Questions q WHERE q.quiz.id = :quizId")
    List<Questions> findAllByQuizId(@Param("quizId") Long quizId);
}
