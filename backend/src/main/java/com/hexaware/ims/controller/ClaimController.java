package com.hexaware.ims.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.ims.dto.ClaimRequestDTO;
import com.hexaware.ims.dto.ClaimResponseDTO;
import com.hexaware.ims.service.IClaimService;

@RestController
@RequestMapping("/api/claim")
public class ClaimController {

    @Autowired
    IClaimService service;

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/")
    public ClaimResponseDTO createClaim(@RequestBody ClaimRequestDTO dto) {
        return service.createClaim(dto);
    }

    @GetMapping("/{id}")
    public ClaimResponseDTO getClaimById(@PathVariable int id) {
        return service.getClaimById(id);
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/userpolicy/{userPolicyId}")
    public List<ClaimResponseDTO> getClaimsByUserPolicy(@PathVariable int userPolicyId){
        return service.getClaimsByUserPolicy(userPolicyId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{claimId}/{status}")
    public ClaimResponseDTO updateClaimStatus(@PathVariable int claimId,
                                              @PathVariable String status) {
        return service.updateClaimStatus(claimId, status);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/")
    public List<ClaimResponseDTO> getAllClaims(){
        return service.getAllClaims();
    }
    
    @GetMapping("/user")
    @PreAuthorize("isAuthenticated()")
    public List<ClaimResponseDTO> getClaimsByUser(Authentication auth) {
        String email = auth.getName();
        return service.getClaimsByUser(email);
    }
    
}
