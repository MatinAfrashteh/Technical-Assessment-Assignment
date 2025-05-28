package com.example.Technical_Assessment_Assignment.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table
public class endUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int user_Id;

    private String name;

    @OneToMany(mappedBy = "endUser")
    List<Claim> claims;

}