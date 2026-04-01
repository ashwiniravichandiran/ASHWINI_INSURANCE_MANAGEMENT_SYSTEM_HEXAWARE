package com.hexaware.ims.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.ims.dto.PolicyRequestDTO;
import com.hexaware.ims.dto.PolicyResponseDTO;
import com.hexaware.ims.service.IPolicyService;

@RestController
@RequestMapping("/api/policies")
public class PolicyController {

    @Autowired
    IPolicyService service;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/add")
    public PolicyResponseDTO addPolicy(@RequestBody PolicyRequestDTO policy) {
        return service.addPolicy(policy);
    }

    @GetMapping("/get/{id}")
    public PolicyResponseDTO getPolicyById(@PathVariable int id) {
        return service.getPolicyById(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public PolicyResponseDTO deletePolicy(@PathVariable int id) {
        return service.deletePolicy(id);
    }

    @GetMapping("/all")
    public List<PolicyResponseDTO> viewAllPolicies() {
        return service.ViewAllPolicies();
    }

    @GetMapping("/vehicletype/{vehicleType}")
    public List<PolicyResponseDTO> getPoliciesByVehicleType(@PathVariable String vehicleType) {
        return service.getPoliciesByVehicleType(vehicleType);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/update/{id}")
    public PolicyResponseDTO updatePolicy(@PathVariable int id,
                                          @RequestBody PolicyRequestDTO policy) {
        return service.updatePolicy(id, policy);
    }
}