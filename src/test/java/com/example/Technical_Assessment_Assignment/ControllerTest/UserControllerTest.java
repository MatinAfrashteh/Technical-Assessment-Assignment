package com.example.Technical_Assessment_Assignment.ControllerTest;

import com.example.Technical_Assessment_Assignment.Configuration.Configuration;
import com.example.Technical_Assessment_Assignment.Controller.UserController;
import com.example.Technical_Assessment_Assignment.DTO.ClaimDTO;
import com.example.Technical_Assessment_Assignment.Model.Claim;
import com.example.Technical_Assessment_Assignment.Model.LostItems;
import com.example.Technical_Assessment_Assignment.Service.ClaimService;
import com.example.Technical_Assessment_Assignment.Service.LostItemsService;
import com.example.Technical_Assessment_Assignment.Service.MockService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.MediaType;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
@Import(Configuration.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LostItemsService lostItemsService;

    @MockitoBean
    private ClaimService claimService;

    @MockitoBean
    private MockService mockService;

    @Test
    @WithMockUser(username = "ali", roles = "USER")
    void whenInvalidItem_thenReturn404() throws Exception {
        ClaimDTO claimDto = new ClaimDTO();
        claimDto.setClaimedQuantity(1);
        claimDto.setItemName("invalid-item");

        Mockito.when(lostItemsService.findByItemName("invalid-item"))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(post("/user/claim")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content(new ObjectMapper().writeValueAsString(claimDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "ali", roles = "USER")
    void whenClaimedQuantityMoreThanAvailable_thenReturn400() throws Exception {

        ClaimDTO claim = new ClaimDTO();
        claim.setClaimedQuantity(5);
        claim.setItemName("Laptop");

        LostItems lostItems = new LostItems();
        lostItems.setItemName("Laptop");
        lostItems.setQuantity(2);

        when(lostItemsService.findByItemName("Laptop")).thenReturn(List.of(lostItems));

        mockMvc.perform(post("/user/claim")
                .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                .content(new ObjectMapper().writeValueAsString(claim)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void whenClaimedQuantityIsZero_thenReturn400() throws Exception {
        ClaimDTO claim = new ClaimDTO();
        claim.setItemName("Laptop");
        claim.setClaimedQuantity(0);

        mockMvc.perform(post("/user/claim")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content(new ObjectMapper().writeValueAsString(claim)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("The number must be greater than zero."));

    }


    @Test
    @WithMockUser(username = "ali", roles = "USER")
    void whenClaimIsValid_thenReturn200() throws Exception{
        ClaimDTO claim = new ClaimDTO();
        claim.setClaimedQuantity(3);
        claim.setItemName("Laptop");

        LostItems lostItems = new LostItems();
        lostItems.setQuantity(5);
        lostItems.setItemName("Laptop");

        when(lostItemsService.findByItemName("Laptop")).thenReturn(List.of(lostItems));
        when(mockService.getUserIdByEmail("ali")).thenReturn(1);
        when(mockService.getUserNameById(1)).thenReturn("Ali");
        when(claimService.saveClaim(any(Claim.class))).thenReturn(new Claim());

        mockMvc.perform(post("/user/claim")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content(new ObjectMapper().writeValueAsString(claim)))
                .andExpect(status().isOk())
                .andExpect(content().string("successful"));

    }

    @Test
    @WithMockUser
    void whenMissingRequiredFields_thenReturn404() throws Exception {
        ClaimDTO invalidClaim = new ClaimDTO();

        mockMvc.perform(post("/user/claim")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content(new ObjectMapper().writeValueAsString(invalidClaim)))
                .andExpect(status().isBadRequest());
    }

}
