package com.hexaware.ims.repo;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexaware.ims.entity.Policy;

public interface PolicyRepo extends JpaRepository<Policy,Integer>{
	public List<Policy> findByVehicleType(String Vehicletype);
}
