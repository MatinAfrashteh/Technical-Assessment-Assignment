package com.example.Technical_Assessment_Assignment.Controller;

import com.example.Technical_Assessment_Assignment.Model.Claim;
import com.example.Technical_Assessment_Assignment.Model.LostItems;
import com.example.Technical_Assessment_Assignment.Service.ClaimService;
import com.example.Technical_Assessment_Assignment.Service.LostItemsService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    LostItemsService lostItemsService;

    @Autowired
    ClaimService claimService;

    @GetMapping("/getClaim")
    public List<Claim> getClaim(){
        return claimService.getAllClaim();
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) throws Exception {

        if (!file.getOriginalFilename().endsWith(".pdf")){
            return ResponseEntity.badRequest().body("Only PDF files are allowed");
        }
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is Empty");
        }

        try {

            String extractedText = extractTextFromPDF(file);

            System.out.println("===Extract PDF File===");
            System.out.println(extractedText);

            Pattern pattern = Pattern.compile("ItemName: (.*?)\\s+Quantity: (\\d+)\\s+Place: (.*?)\\s");
            Matcher matcher = pattern.matcher(extractedText);

            boolean found = false;
            while (matcher.find()) {
                String ItemName = matcher.group(1).trim();
                int Quantity = Integer.parseInt(matcher.group(2).trim());
                String Place = matcher.group(3).trim();

                LostItems lostItems = new LostItems();
                lostItems.setItemName(ItemName);
                lostItems.setQuantity(Quantity);
                lostItems.setPlace(Place);

                lostItemsService.saveLostItems(lostItems);
                found = true;

            }
            if (found) {
                return ResponseEntity.ok("Successful Upload File");
            } else {
                return ResponseEntity.status(400).body("Missing or invalid national ID.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to Upload" + e.getMessage());
        }
    }

    public String extractTextFromPDF(MultipartFile file) {
        try {
            PDDocument pdDocument = PDDocument.load(file.getInputStream());
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(pdDocument);
        } catch (IOException e) {
            throw new RuntimeException("File Details Not Found " + e);
        }
    }

}
