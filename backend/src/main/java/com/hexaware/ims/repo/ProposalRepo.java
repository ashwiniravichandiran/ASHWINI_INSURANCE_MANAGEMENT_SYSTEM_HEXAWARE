package com.hexaware.ims.repo;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexaware.ims.entity.Proposal;
import com.hexaware.ims.entity.User;

public interface ProposalRepo extends JpaRepository<Proposal,Integer>{
	List<Proposal> findByUserUserId(int userId);
	List<Proposal> findByStatus(String status);
	List<Proposal> findByUser(User user);
}
