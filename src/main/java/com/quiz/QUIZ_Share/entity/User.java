package com.quiz.QUIZ_Share.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "login")
public class User {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(unique = true)
    private String username;

    @Column(unique = true, nullable = true)
    private String email;

    private String firstName;
    private String lastName;

    @Column(nullable = true)
    private String imageUrl;

    @Column(name = "results")
    private List<Integer> results;
}
