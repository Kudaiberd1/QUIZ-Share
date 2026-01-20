package com.quiz.QUIZ_Share.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.quiz.QUIZ_Share.enums.Difficulty;
import com.quiz.QUIZ_Share.enums.Privacy;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "quiz")
public class Quiz {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "title", nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty", nullable = false)
    private Difficulty difficulty;

    @Column(name = "subject", nullable = false)
    private String subject;

    @Column(name = "description", nullable = true, length = 2000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "privacy", nullable = false)
    private Privacy privacy;

    @Column(name = "rate")
    private List<Integer> rate =  new  ArrayList<>(List.of(0,0,0,0,0));

    @OneToMany(mappedBy = "quiz", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    @JsonIgnore
    private List<Questions> questions;

    @Column(name = "UserId", nullable = false)
    private Integer userId;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "addedTime")
    private Date addedTime;

    @Column(name = "imageUrl")
    private String imageUrl;

    @Column(name = "takeTimeLimit",  nullable = true)
    private Integer takeTimeLimit;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TakenQuiz> takenQuiz;

    @PrePersist
    protected void onCreate() {
        this.addedTime = new Date();
    }
}
