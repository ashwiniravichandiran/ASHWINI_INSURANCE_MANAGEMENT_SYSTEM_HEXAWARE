package com.hexaware.ims.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.ims.dto.UserPolicyResponseDTO;
import com.hexaware.ims.service.IUserPolicyService;

@RestController
@RequestMapping("/api/userpolicies")
public class UserPolicyController {

    @Autowired
    IUserPolicyService service;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/create/{proposalId}")
    public UserPolicyResponseDTO createUserPolicy(@PathVariable int proposalId) {
        return service.createUserPolicy(proposalId);
    }

    @GetMapping("/get/{id}")
    public UserPolicyResponseDTO getPolicyById(@PathVariable int id) {
        return service.getPolicyById(id);
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/user/{userId}")
    public List<UserPolicyResponseDTO> getPoliciesByUser(@PathVariable int userId) {
        return service.getPoliciesByUser(userId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/update/{policyId}/{status}")
    public UserPolicyResponseDTO updatePolicyStatus(@PathVariable int policyId,
                                                    @PathVariable String status) {
        return service.updatePolicyStatus(policyId, status);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/all")
    public List<UserPolicyResponseDTO> getAllPolicies() {
        return service.getAllPolicies();
    }
    
    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/policy/by-proposal/{proposalId}")
    public UserPolicyResponseDTO getPolicyByProposal(@PathVariable int proposalId) {
        return service.getByProposalId(proposalId);
    }
    
    
}