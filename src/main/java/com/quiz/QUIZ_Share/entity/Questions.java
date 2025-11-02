package com.quiz.QUIZ_Share.entity;

import com.quiz.QUIZ_Share.enums.Answer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "questions")
public class Questions {

    @Id
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

    @OneToOne(mappedBy = "question")
    private Quiz quiz;

}
