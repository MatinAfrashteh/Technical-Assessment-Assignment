package com.example.Technical_Assessment_Assignment.Service;

import com.example.Technical_Assessment_Assignment.Model.Claim;
import com.example.Technical_Assessment_Assignment.Repository.ClaimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClaimService {

    @Autowired
    ClaimRepository claimRepository;

    public List<Claim> getAllClaim() {
        return claimRepository.findAll();
    }

    public Claim saveClaim(Claim claim) throws Exception {
        if (claim == null) {
            throw new Exception("Claim is null");
        }

        if ( claim.getClaimedQuantity() <= 0) {
            throw new IllegalArgumentException("Invalid claim data: ClaimedQuantity should be greater than 0");
        }

        Claim saved = claimRepository.save(claim);
        if (saved == null) {
            throw new Exception("Save failed: returned null");
        }

        return saved;
    }


}
