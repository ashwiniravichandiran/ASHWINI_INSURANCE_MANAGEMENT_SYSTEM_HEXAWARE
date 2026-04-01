package com.hexaware.ims.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.ims.dto.ClaimRequestDTO;
import com.hexaware.ims.dto.ClaimResponseDTO;
import com.hexaware.ims.entity.Claim;
import com.hexaware.ims.entity.UserPolicy;
import com.hexaware.ims.exception.ClaimNotFoundException;
import com.hexaware.ims.exception.UserPolicyNotFoundException;
import com.hexaware.ims.repo.ClaimRepo;
import com.hexaware.ims.repo.UserPolicyRepo;

@Service
public class ClaimServiceImpl implements IClaimService {

    @Autowired
    ClaimRepo crepo;

    @Autowired
    UserPolicyRepo userPolicyRepo;
    
    @Autowired
    EmailService emailService;

    public Claim convertToEntity(ClaimRequestDTO dto, UserPolicy userPolicy) {

        Claim claim = new Claim();
        claim.setUserPolicy(userPolicy);
        claim.setClaimAmount(dto.getClaimAmount());
        claim.setReason(dto.getReason());
        claim.setStatus("PENDING");
        claim.setCreatedDate(LocalDateTime.now());

        return claim;
    }

    public ClaimResponseDTO convertToDTO(Claim claim) {

        ClaimResponseDTO dto = new ClaimResponseDTO();

        dto.setClaimId(claim.getClaimId());
        dto.setUserPolicyId(claim.getUserPolicy().getUserPolicyId());
        dto.setClaimAmount(claim.getClaimAmount());
        dto.setReason(claim.getReason());
        dto.setStatus(claim.getStatus());
        dto.setCreatedDate(claim.getCreatedDate());
        dto.setVehicleNumber(claim.getUserPolicy().getProposal().getVehicleNumber());
        dto.setVehicleModel(claim.getUserPolicy().getProposal().getVehicleModel());
        return dto;
    }

    @Override
    public ClaimResponseDTO createClaim(ClaimRequestDTO dto) {

    	List<Claim> existing = crepo.findByUserPolicyUserPolicyId(dto.getUserPolicyId());

        boolean hasPending = existing.stream()
            .anyMatch(c -> c.getStatus().equals("PENDING"));

        if (hasPending) {
            throw new RuntimeException("Claim already pending for this policy");
        }
        
        UserPolicy userPolicy = userPolicyRepo
                .findById(dto.getUserPolicyId())
                .orElseThrow(() -> new UserPolicyNotFoundException(
                        "UserPolicy not found with id " + dto.getUserPolicyId()));

        Claim claim = convertToEntity(dto, userPolicy);

        Claim saved = crepo.save(claim);

        return convertToDTO(saved);
    }

    @Override
    public ClaimResponseDTO getClaimById(int id) {

        Claim claim = crepo.findById(id)
                .orElseThrow(() -> new ClaimNotFoundException(
                        "Claim not found with id " + id));

        return convertToDTO(claim);
    }

    @Override
    public List<ClaimResponseDTO> getClaimsByUserPolicy(int userPolicyId) {

        return crepo.findByUserPolicyUserPolicyId(userPolicyId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ClaimResponseDTO updateClaimStatus(int claimId, String status) {

        Claim claim = crepo.findById(claimId)
                .orElseThrow(() -> new ClaimNotFoundException(
                        "Claim not found with id " + claimId));

        claim.setStatus(status);

        Claim updated = crepo.save(claim);

        String email = claim.getUserPolicy().getUser().getEmail();

        emailService.sendClaimStatusEmail(email, status);

        return convertToDTO(updated);
    }

    @Override
    public List<ClaimResponseDTO> getAllClaims() {

        return crepo.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<ClaimResponseDTO> getClaimsByUser(String email) {

        List<UserPolicy> policies = userPolicyRepo.findByUserEmail(email);

        return policies.stream()
                .flatMap(p -> crepo
                    .findByUserPolicyUserPolicyId(p.getUserPolicyId())
                    .stream())
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}