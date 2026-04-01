package com.hexaware.ims.service;

import java.util.List;

import com.hexaware.ims.dto.PolicyRequestDTO;
import com.hexaware.ims.dto.PolicyResponseDTO;

public interface IPolicyService {
	public PolicyResponseDTO addPolicy(PolicyRequestDTO policy);
	public PolicyResponseDTO getPolicyById(int id);
	public PolicyResponseDTO deletePolicy(int id);
	public List<PolicyResponseDTO> ViewAllPolicies();
	PolicyResponseDTO updatePolicy(int id, PolicyRequestDTO policy);
	List<PolicyResponseDTO> getPoliciesByVehicleType(String vehicleType);
}
