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
public class LostItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    private String ItemName;
    private int Quantity;
    private String Place;

    @OneToMany(mappedBy = "lostItems")
    List<Claim> claims;
}