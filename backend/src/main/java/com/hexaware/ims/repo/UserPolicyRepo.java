package com.hexaware.ims.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexaware.ims.entity.Proposal;
import com.hexaware.ims.entity.UserPolicy;

public interface UserPolicyRepo extends JpaRepository<UserPolicy,Integer>{
	List<UserPolicy> findByProposalUserUserId(int userId);
	boolean existsByProposal(Proposal proposal);
	Optional<UserPolicy> findByProposalProposalId(int proposalId);
	List<UserPolicy> findByUserEmail(String email);
}
