package com.example.Technical_Assessment_Assignment.ServiceTest;

import com.example.Technical_Assessment_Assignment.Model.Claim;
import com.example.Technical_Assessment_Assignment.Repository.ClaimRepository;
import com.example.Technical_Assessment_Assignment.Service.ClaimService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ClaimServiceTest {


    @Mock
    ClaimRepository claimRepository;

    @InjectMocks
    ClaimService claimService;

    Claim fakeClaim;

    @BeforeEach
    void setup() {
        fakeClaim = new Claim(1, null, null, 2);

    }

    @Test
    void shouldReturnListWithOneClaim_whenSingleClaimExists() {

        when(claimRepository.findAll()).thenReturn(List.of(fakeClaim));
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

        when(claimRepository.findAll()).thenReturn(List.of(fakeClaim));
        List<Claim> result = claimService.getAllClaim();

        Assertions.assertEquals(fakeClaim,result.get(0));

    }

    @Test
    void shouldSaveValidClaimSuccessfully() throws Exception {
        when(claimRepository.save(fakeClaim)).thenReturn(fakeClaim);
        Claim claims = claimService.saveClaim(fakeClaim);
        Assertions.assertEquals(fakeClaim,claims);
        verify(claimRepository).save(fakeClaim);
    }

    @Test
    void shouldThrowException_whenRepositoryReturnsNullOnSave() {
        when(claimRepository.save(fakeClaim)).thenReturn(null);
        assertThrows(Exception.class, () -> claimService.saveClaim(fakeClaim), "data should not null");

    }

    @Test
    void shouldThrowException_whenSavingInvalidClaim() {
        Claim invalidClaim = new Claim(1, null, null, 0);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> {claimService.saveClaim(invalidClaim);},"data should not null");
        verify(claimRepository, never()).save(any());


    }
}