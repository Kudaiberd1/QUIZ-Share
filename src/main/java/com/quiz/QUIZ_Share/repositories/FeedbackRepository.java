package com.quiz.QUIZ_Share.repositories;

import com.quiz.QUIZ_Share.entity.Feedback;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
    Feedback findByAuthorIdAndQuizId(Integer authorId, Integer quizId);

    List<Feedback> findAllByQuizId(Long quizId);

    List<Feedback> findAllByToUserId(Long toUserId, Sort sort);
}
