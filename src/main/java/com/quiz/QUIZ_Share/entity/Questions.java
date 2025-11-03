package com.quiz.QUIZ_Share.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.quiz.QUIZ_Share.enums.Answer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "questions")
public class Questions {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String question;

    @Column(name = "optionA", nullable = false)
    private String optionA;

    @Column(name = "optionB", nullable = false)
    private String optionB;

    @Column(name = "optionC", nullable = false)
    private String optionC;

    @Column(name = "optionD", nullable = false)
    private String optionD;

    @Column(name = "answer", nullable = false)
    @Enumerated(EnumType.STRING)
    private Answer answer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Quiz quiz;
}
