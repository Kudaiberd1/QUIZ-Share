package com.quiz.QUIZ_Share.repositories;

import com.quiz.QUIZ_Share.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {

    @Query("SELECT DISTINCT q FROM Quiz q " +
            "LEFT JOIN FETCH q.questions " +
            "LEFT JOIN FETCH q.user")
    List<Quiz> findAllWithQuestions();
}
