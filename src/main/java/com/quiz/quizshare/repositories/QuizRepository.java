package com.quiz.quizshare.repositories;

import com.quiz.quizshare.entity.Quiz;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {

    List<Quiz> findAllByOrderByAddedTimeDesc();

    @Query("""
        SELECT DISTINCT q.subject FROM Quiz q
    """)
    List<String> findAllSubject();

    @Query("""
        SELECT q FROM Quiz q
        WHERE (:subject IS NULL OR q.subject = :subject)
          AND (:title IS NULL OR :title = '' OR UPPER(q.title) LIKE CONCAT(UPPER(:title), '%'))
    """)
    List<Quiz> filterQuiz(@Param("title") String title, @Param("subject") String subject, Sort sort);
}
