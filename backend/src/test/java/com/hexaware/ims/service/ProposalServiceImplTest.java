package com.hexaware.ims.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hexaware.ims.dto.ProposalRequestDTO;
import com.hexaware.ims.dto.ProposalResponseDTO;

@SpringBootTest
class ProposalServiceImplTest {

    @Autowired
    IProposalService service;

    @Test
    @Disabled
    void testAddProposal() {

        ProposalRequestDTO dto = new ProposalRequestDTO();

        dto.setPolicyId(1);
        dto.setVehicleType("Car");
        dto.setVehicleNumber("TN58AB1234");
        dto.setVehicleModel("Hyundai");

        ProposalResponseDTO response = service.addProposal(dto, "iok@gmail.com");

        assertNotNull(response);
        assertEquals("PENDING", response.getStatus());
    }

    @Test
    void testGetProposalById() {

        ProposalResponseDTO proposal = service.getProposalById(1);

        assertNotNull(proposal);
    }

    @Test
    void testGetProposalByUserId() {

        List<ProposalResponseDTO> proposals = service.getProposalByUserId(1);

        assertNotNull(proposals);
    }

    @Test
    void testUpdateStatus() {

        ProposalResponseDTO proposal = service.updateStatus(1, "APPROVED");

        assertEquals("APPROVED", proposal.getStatus());
    }

    @Test
    void testGenerateQuote() {

        ProposalResponseDTO proposal = service.generateQuote(1);

        assertEquals("QUOTE_GENERATED", proposal.getStatus());
    }

}