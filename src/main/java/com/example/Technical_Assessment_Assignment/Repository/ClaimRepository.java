package com.example.Technical_Assessment_Assignment.Repository;

import com.example.Technical_Assessment_Assignment.Model.Claim;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClaimRepository extends JpaRepository<Claim,Integer> {
}
