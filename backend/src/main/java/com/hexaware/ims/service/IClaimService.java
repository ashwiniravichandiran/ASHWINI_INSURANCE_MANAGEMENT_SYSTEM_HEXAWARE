package com.hexaware.ims.service;

import java.util.List;

import com.hexaware.ims.dto.ClaimRequestDTO;
import com.hexaware.ims.dto.ClaimResponseDTO;

public interface IClaimService {

    ClaimResponseDTO createClaim(ClaimRequestDTO dto);

    ClaimResponseDTO getClaimById(int id);

    List<ClaimResponseDTO> getClaimsByUserPolicy(int userPolicyId);

    ClaimResponseDTO updateClaimStatus(int claimId, String status);

    List<ClaimResponseDTO> getAllClaims();
    List<ClaimResponseDTO> getClaimsByUser(String email);
}