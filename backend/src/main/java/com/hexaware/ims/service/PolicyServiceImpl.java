package com.hexaware.ims.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.ims.dto.PolicyRequestDTO;
import com.hexaware.ims.dto.PolicyResponseDTO;
import com.hexaware.ims.entity.Policy;
import com.hexaware.ims.exception.PolicyNotFoundException;
import com.hexaware.ims.repo.PolicyRepo;

@Service
public class PolicyServiceImpl implements IPolicyService {

    @Autowired
    PolicyRepo repo;

    public Policy convertToEntity(PolicyRequestDTO dto) {

        Policy policy = new Policy();
        policy.setPolicyName(dto.getPolicyName());
        policy.setDescription(dto.getDescription());
        policy.setBasePremium(dto.getBasePremium());
        policy.setVehicleType(dto.getVehicleType());
        
        return policy;
    }

    public PolicyResponseDTO convertToDTO(Policy policy) {

        PolicyResponseDTO dto = new PolicyResponseDTO();
        dto.setPolicyId(policy.getPolicyId());
        dto.setPolicyName(policy.getPolicyName());
        dto.setDescription(policy.getDescription());
        dto.setBasePremium(policy.getBasePremium());
        dto.setVehicleType(policy.getVehicleType());
        
        return dto;
    }

    @Override
    public PolicyResponseDTO addPolicy(PolicyRequestDTO dto) {

        Policy policy = convertToEntity(dto);

        Policy saved = repo.save(policy);

        return convertToDTO(saved);
    }

    @Override
    public PolicyResponseDTO getPolicyById(int id) {

        Optional<Policy> policy = repo.findById(id);

        if(policy.isPresent()) {
            return convertToDTO(policy.get());
        }
        else {
        	throw new PolicyNotFoundException("Policy not found with with id "+id);
        }
    }

    @Override
    public PolicyResponseDTO deletePolicy(int id) {

        Optional<Policy> policy = repo.findById(id);

        if(policy.isPresent()) {

            repo.deleteById(id);

            return convertToDTO(policy.get());
        }
        else {
        	throw new PolicyNotFoundException("Policy not found with with id "+id);
        }
    }

    @Override
    public List<PolicyResponseDTO> ViewAllPolicies() {

        return repo.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<PolicyResponseDTO> getPoliciesByVehicleType(String vehicleType) {
    	
        return repo.findByVehicleType(vehicleType).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public PolicyResponseDTO updatePolicy(int id, PolicyRequestDTO dto) {

        Optional<Policy> existing = repo.findById(id);

        if(existing.isPresent()) {

            Policy policy = existing.get();

            policy.setPolicyName(dto.getPolicyName());
            policy.setDescription(dto.getDescription());
            policy.setBasePremium(dto.getBasePremium());

            Policy updated = repo.save(policy);

            return convertToDTO(updated);
        }
        else {
        	throw new PolicyNotFoundException("Policy not found with with id "+id);
        }
    }
}