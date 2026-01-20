package com.quiz.quizshare.repositories;

import com.quiz.quizshare.entity.TakenQuiz;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TakenQuizRepository extends JpaRepository<TakenQuiz,Long> {
    List<TakenQuiz> getByAuthorId(Integer authorId);

    List<TakenQuiz> findAllByAuthorId(Integer authorId);

    List<TakenQuiz> findAllByAuthorId(Integer authorId, Sort sort);

    @Query("SELECT t FROM TakenQuiz t WHERE t.quiz.id = :quizId")
    List<TakenQuiz> findAllByQuizId(@Param("quizId") Long quizId);
}
