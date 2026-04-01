package com.hexaware.ims.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.ims.dto.UserPolicyResponseDTO;
import com.hexaware.ims.entity.Proposal;
import com.hexaware.ims.entity.User;
import com.hexaware.ims.entity.UserPolicy;
import com.hexaware.ims.exception.ProposalNotFoundException;
import com.hexaware.ims.exception.UserPolicyNotFoundException;
import com.hexaware.ims.repo.ProposalRepo;
import com.hexaware.ims.repo.UserPolicyRepo;

@Service
public class UserPolicyServiceImpl implements IUserPolicyService {

    @Autowired
    UserPolicyRepo repo;

    @Autowired
    ProposalRepo proposalRepo;
    
    @Autowired
    EmailService emailService;

    public UserPolicyResponseDTO convertToDTO(UserPolicy userPolicy) {

        UserPolicyResponseDTO dto = new UserPolicyResponseDTO();

        dto.setUserPolicyId(userPolicy.getUserPolicyId());
        dto.setProposalId(userPolicy.getProposal().getProposalId());
        dto.setStatus(userPolicy.getStatus());

        return dto;
    }

    @Override
    public UserPolicyResponseDTO createUserPolicy(int proposalId) {

        Proposal proposal = proposalRepo.findById(proposalId)
                .orElseThrow(() -> new ProposalNotFoundException(
                        "Proposal not found with id " + proposalId));
        User user = proposal.getUser();

        UserPolicy policy = new UserPolicy();

        policy.setProposal(proposal);
        policy.setStatus("ACTIVE");
        policy.setUser(user);

        UserPolicy saved = repo.save(policy);
        
        emailService.sendPolicyCreatedEmail(user.getEmail());

        return convertToDTO(saved);
    }

    @Override
    public UserPolicyResponseDTO getPolicyById(int id) {

        UserPolicy policy = repo.findById(id)
                .orElseThrow(() -> new UserPolicyNotFoundException(
                        "UserPolicy not found with id " + id));
        
        return convertToDTO(policy);
    }

    @Override
    public List<UserPolicyResponseDTO> getPoliciesByUser(int userId) {

        List<UserPolicy> policies = repo.findByProposalUserUserId(userId);

        return policies.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserPolicyResponseDTO updatePolicyStatus(int policyId, String status) {

        UserPolicy policy = repo.findById(policyId)
                .orElseThrow(() -> new UserPolicyNotFoundException(
                        "UserPolicy not found with id " + policyId));

        policy.setStatus(status);

        UserPolicy updated = repo.save(policy);

        return convertToDTO(updated);
    }

    @Override
    public List<UserPolicyResponseDTO> getAllPolicies() {

        List<UserPolicy> policies = repo.findAll();

        return policies.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

	@Override
	public UserPolicyResponseDTO getByProposalId(int proposalId) {
		// TODO Auto-generated method stub

	        UserPolicy policy = repo.findByProposalProposalId(proposalId)
	                .orElseThrow(() -> new RuntimeException("Policy not found for this proposal"));

	        return new UserPolicyResponseDTO(
	                policy.getUserPolicyId(),
	                policy.getProposal().getProposalId(),
	                policy.getStatus()
	        );
	    }
	
	
    
}