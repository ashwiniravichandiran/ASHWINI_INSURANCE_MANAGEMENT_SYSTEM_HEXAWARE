package com.hexaware.ims.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexaware.ims.entity.Claim;
public interface ClaimRepo extends JpaRepository<Claim,Integer>{
	
	List<Claim> findByUserPolicyUserPolicyId(int id);

}
