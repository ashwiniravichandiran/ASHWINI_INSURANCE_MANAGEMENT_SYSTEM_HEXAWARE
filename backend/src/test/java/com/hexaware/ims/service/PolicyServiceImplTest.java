package com.hexaware.ims.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hexaware.ims.dto.PolicyRequestDTO;
import com.hexaware.ims.dto.PolicyResponseDTO;

@SpringBootTest
class PolicyServiceImplTest {

    @Autowired
    IPolicyService service;

    @Test
    @Disabled
    void testAddPolicy() {

        PolicyRequestDTO policy = new PolicyRequestDTO(
                "Bike Insurance 2",
                "for accidents",
                500,"Bike"
        );

        PolicyResponseDTO saved = service.addPolicy(policy);

        assertNotNull(saved);
        assertEquals(policy.getPolicyName(), saved.getPolicyName());
    }

    @Test
    @Disabled
    void testGetPolicyById() {

        PolicyResponseDTO policy = service.getPolicyById(1);

        assertNotNull(policy);
    }

    @Test
    @Disabled
    void testDeletePolicy() {

        PolicyResponseDTO policy = service.deletePolicy(1);

        assertNotNull(policy);
    }
    
    @Test
    void updatePolicy() {
    	PolicyRequestDTO policy = new PolicyRequestDTO(
                "Updated Car Insurance",
                "Updated accident coverage",
                6000,
                "CAR"
        );

        PolicyResponseDTO updated = service.updatePolicy(2, policy);

        assertNotNull(updated);
        assertEquals("Updated Car Insurance", updated.getPolicyName());
    }

    @Test
    @Disabled
    void testGetPoliciesByVehicleType() {

        List<PolicyResponseDTO> policies = service.getPoliciesByVehicleType("Car");

        assertNotNull(policies);
    }
}