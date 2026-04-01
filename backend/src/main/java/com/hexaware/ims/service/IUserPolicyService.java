package com.hexaware.ims.service;

import java.util.List;

import com.hexaware.ims.dto.UserPolicyResponseDTO;

public interface IUserPolicyService {

    UserPolicyResponseDTO createUserPolicy(int proposalId);

    UserPolicyResponseDTO getPolicyById(int id);

    List<UserPolicyResponseDTO> getPoliciesByUser(int userId);

    UserPolicyResponseDTO updatePolicyStatus(int policyId, String status);
    
    List<UserPolicyResponseDTO> getAllPolicies();
    UserPolicyResponseDTO getByProposalId(int proposalId);
    

}