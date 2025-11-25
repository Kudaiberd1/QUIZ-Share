package com.quiz.QUIZ_Share.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.Date;

import static com.fasterxml.jackson.databind.type.LogicalType.DateTime;

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

    @Column(name = "is_readed")
    private Boolean isReaded;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.isReaded = false;
        this.createdAt = LocalDateTime.now();
    }
}
