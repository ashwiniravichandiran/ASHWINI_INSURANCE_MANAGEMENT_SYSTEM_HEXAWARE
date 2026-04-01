package com.hexaware.ims.repo;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexaware.ims.entity.Payment;
import com.hexaware.ims.entity.Proposal;

public interface PaymentRepo extends JpaRepository<Payment,Integer>{
	public List<Payment> findByProposalUserUserId(int userid);
	public List<Payment> findByProposalProposalId(int proposal_id);
	boolean existsByProposal(Proposal proposal);
}
