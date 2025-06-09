package com.example.Technical_Assessment_Assignment.Controller;

import com.example.Technical_Assessment_Assignment.DTO.ClaimDTO;
import com.example.Technical_Assessment_Assignment.Model.Claim;
import com.example.Technical_Assessment_Assignment.Model.LostItems;
import com.example.Technical_Assessment_Assignment.Service.ClaimService;
import com.example.Technical_Assessment_Assignment.Service.LostItemsService;
import com.example.Technical_Assessment_Assignment.Service.MockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    LostItemsService lostItemsService;
    @Autowired
    ClaimService claimService;
    @Autowired
    MockService mockService;

    @GetMapping("/getLostItems")
    public List<LostItems> getAllLostItems(){
        return lostItemsService.getAllLostItems();
    }

    @PostMapping("/claim")
    public ResponseEntity<String> claimItem(@RequestBody ClaimDTO claimDTO) throws Exception {
        int quantity = claimDTO.getClaimedQuantity();
        if (quantity < 1) {
            return ResponseEntity.status(400).body("The number must be greater than zero.");
        }

        String itemName = claimDTO.getItemName();
        List<LostItems> lostItemList = lostItemsService.findByItemName(itemName);

        if (lostItemList == null || lostItemList.isEmpty()) {
            return ResponseEntity.status(404).body("Item not found");
        }

        LostItems lostItem = lostItemList.get(0);

        if (quantity > lostItem.getQuantity()) {
            return ResponseEntity.status(400).body("Requested quantity exceeds available stock.");
        }

        Claim claim = new Claim();
        claim.setClaimedQuantity(quantity);
        claim.setLostItems(lostItem);

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        int userId = mockService.getUserIdByEmail(email);
        String userName = mockService.getUserNameById(userId);

        claim.setClaimByName(userName);

        Claim saved = claimService.saveClaim(claim);
        if (saved != null) {
            return ResponseEntity.ok("successful");
        } else {
            return ResponseEntity.status(404).body("this item is not found");
        }
    }

}



