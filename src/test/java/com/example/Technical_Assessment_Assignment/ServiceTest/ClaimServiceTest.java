package com.example.Technical_Assessment_Assignment.ServiceTest;

import com.example.Technical_Assessment_Assignment.Model.Claim;
import com.example.Technical_Assessment_Assignment.Repository.ClaimRepository;
import com.example.Technical_Assessment_Assignment.Service.ClaimService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ClaimServiceTest {


    @Mock
    ClaimRepository claimRepository;

    @InjectMocks
    ClaimService claimService;

    Claim fakeClaims;

    @BeforeEach
    void setup() {
        fakeClaims = new Claim(1,null,null,null,2);

    }

    @Test
    void shouldReturnListWithOneClaim_whenSingleClaimExists() {

        when(claimRepository.findAll()).thenReturn(List.of(fakeClaims));
        Assertions.assertEquals(1, claimService.getAllClaim().size());

    }

    @Test
    void shouldReturnEmptyList_whenNoClaimsExist() {

        when(claimRepository.findAll()).thenReturn(Collections.emptyList());
        List<Claim> result = claimService.getAllClaim();
        Assertions.assertTrue(result.isEmpty());

    }

    @Test
    void shouldReturnExpectedClaimFromList_whenClaimsArePresent() {

        when(claimRepository.findAll()).thenReturn(List.of(fakeClaims));
        List<Claim> result = claimService.getAllClaim();

        Assertions.assertEquals(fakeClaims,result.get(0));

    }

    @Test
    void shouldSaveValidClaimSuccessfully() throws Exception {
        when(claimRepository.save(fakeClaims)).thenReturn(fakeClaims);
        Claim claims = claimService.saveClaim(fakeClaims);
        Assertions.assertEquals(fakeClaims,claims);
        verify(claimRepository).save(fakeClaims);
    }

    @Test
    void shouldThrowException_whenRepositoryReturnsNullOnSave() {
        when(claimRepository.save(fakeClaims)).thenReturn(null);
        assertThrows(Exception.class, () -> claimService.saveClaim(fakeClaims), "data should not null");

    }

    @Test
    void shouldThrowException_whenSavingInvalidClaim() {
        Claim invalidClaim = new Claim(1, null, null, null,-1);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> {claimService.saveClaim(invalidClaim);},"data should not null");
        verify(claimRepository, never()).save(any());


    }
}