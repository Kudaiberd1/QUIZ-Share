package com.quiz.QUIZ_Share.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.quiz.QUIZ_Share.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
