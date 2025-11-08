package com.quiz.QUIZ_Share.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.quiz.QUIZ_Share.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private Integer id;

    private String email;
    private String password;

    private String firstName;
    private String lastName;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return email;
    }
}
