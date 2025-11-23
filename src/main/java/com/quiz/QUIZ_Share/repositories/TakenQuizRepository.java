package com.quiz.QUIZ_Share.repositories;

import com.quiz.QUIZ_Share.entity.TakenQuiz;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TakenQuizRepository extends JpaRepository<TakenQuiz,Long> {
    List<TakenQuiz> getByAuthorId(Integer authorId);

    List<TakenQuiz> findAllByAuthorId(Integer authorId);

    List<TakenQuiz> findAllByAuthorId(Integer authorId, Sort sort);
}
