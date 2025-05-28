package com.example.Technical_Assessment_Assignment.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table
public class Claim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int claim_Id;

    @ManyToOne
    @JoinColumn(name = "user_Id")
    private endUser endUser;

    @ManyToOne
    @JoinColumn(name = "lost_Items")
    private LostItems lostItems;

    private String claimedQuantity;

}
