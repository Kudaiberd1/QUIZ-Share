package com.quiz.quizshare.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "feedback"
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

    @Column(name = "toUserId")
    private Long toUserId;

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
