package com.quiz.QUIZ_Share.repositories;

import com.quiz.QUIZ_Share.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
    Feedback findByAuthorIdAndQuizId(Integer authorId, Integer quizId);
}
