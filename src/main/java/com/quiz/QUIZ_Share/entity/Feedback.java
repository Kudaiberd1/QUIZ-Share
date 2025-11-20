package com.quiz.QUIZ_Share.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "feedback",
        uniqueConstraints = @UniqueConstraint(columnNames = {"author_id", "quiz_id"})
)
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "feedback")
    private String feedback;

    @Column(name = "star")
    private Integer star;

    @Column(name = "authorId")
    private Long authorId;

    @Column(name = "quizId")
    private Long quizId;
}
