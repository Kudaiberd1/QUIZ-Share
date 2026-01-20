package com.quiz.QUIZ_Share.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "takenQuiz")
public class TakenQuiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "taken_quiz_id")
    private List<Answer> userAnswers = new ArrayList<>();

    @Column(name = "correct")
    private Integer correct;

    @Column(name = "wrong")
    private Integer wrong;

    @Column(name = "skipped")
    private Integer skipped;

    @Column(name = "rating")
    private double rating;

    @Column(name = "authorId")
    private Integer authorId;
}
