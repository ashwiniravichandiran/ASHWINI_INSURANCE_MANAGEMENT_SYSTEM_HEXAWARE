package com.hexaware.ims.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hexaware.ims.dto.ClaimRequestDTO;
import com.hexaware.ims.dto.ClaimResponseDTO;

@SpringBootTest
class ClaimServiceImplTest {

    @Autowired
    IClaimService service;

    @Test
    void testCreateClaim() {

        ClaimRequestDTO dto = new ClaimRequestDTO();

        dto.setUserPolicyId(1);
        dto.setClaimAmount(5000);
        dto.setReason("Accident");

        ClaimResponseDTO saved = service.createClaim(dto);

        assertNotNull(saved);
        assertEquals("PENDING", saved.getStatus());
    }

    @Test
    void testGetClaimById() {

        ClaimResponseDTO claim = service.getClaimById(1);

        assertNotNull(claim);
    }

    @Test
    void testGetClaimsByUserPolicy() {

        List<ClaimResponseDTO> claims = service.getClaimsByUserPolicy(1);

        assertNotNull(claims);
    }

    @Test
    void testUpdateClaimStatus() {

        ClaimResponseDTO claim = service.updateClaimStatus(1, "APPROVED");

        assertEquals("APPROVED", claim.getStatus());
    }
}