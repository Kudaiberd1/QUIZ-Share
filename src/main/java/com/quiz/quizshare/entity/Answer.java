package com.quiz.quizshare.entity;

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
@Table(name = "answers")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int questionIndex;

    @ElementCollection
    @CollectionTable(name = "answer_items", joinColumns = @JoinColumn(name = "answer_id"))
    @Column(name = "value")
    private List<Integer> selectedOptions = new ArrayList<>();
}