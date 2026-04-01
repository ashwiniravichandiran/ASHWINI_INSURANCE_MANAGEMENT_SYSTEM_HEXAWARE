package com.hexaware.ims.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hexaware.ims.dto.UserPolicyResponseDTO;

@SpringBootTest
class UserPolicyServiceImplTest {

    @Autowired
    IUserPolicyService service;

    @Test
    @Disabled
    void testCreateUserPolicy() {

        UserPolicyResponseDTO policy = service.createUserPolicy(1);

        assertNotNull(policy);
        assertEquals("ACTIVE", policy.getStatus());
    }

    @Test
    void testGetPolicyById() {

        UserPolicyResponseDTO policy = service.getPolicyById(1);

        assertNotNull(policy);
    }

    @Test
    void testGetPoliciesByUser() {

        List<UserPolicyResponseDTO> policies = service.getPoliciesByUser(1);

        assertNotNull(policies);
    }

    @Test
    void testUpdatePolicyStatus() {

        UserPolicyResponseDTO policy = service.updatePolicyStatus(1, "EXPIRED");

        assertEquals("EXPIRED", policy.getStatus());
    }
    
    @Test
    void testGetByProposalId() {

        int proposalId = 1; // موجود in your DB

        UserPolicyResponseDTO policy = service.getByProposalId(proposalId);

        assertNotNull(policy);
        assertEquals(1, policy.getUserPolicyId());
        assertEquals(proposalId, policy.getProposalId());
        assertEquals("EXPIRED", policy.getStatus());
    }
    
}