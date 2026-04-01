package com.hexaware.ims.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hexaware.ims.dto.PaymentRequestDTO;
import com.hexaware.ims.dto.PaymentResponseDTO;

@SpringBootTest
class PaymentServiceImplTest {

    @Autowired
    IPaymentService service;

    @Test
    @Disabled
    void testMakePayment() {

        PaymentRequestDTO dto = new PaymentRequestDTO();

        dto.setProposalId(1);
        dto.setAmount(8000);

        PaymentResponseDTO payment = service.makePayment(dto);

        assertNotNull(payment);
        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testGetPaymentById() {

        PaymentResponseDTO payment = service.getPaymentById(1);

        assertNotNull(payment);
    }

    @Test
    void testGetPaymentsByProposal() {

        List<PaymentResponseDTO> payments = service.getPaymentsByProposal(1);

        assertNotNull(payments);
    }

    @Test
    void testUpdatePaymentStatus() {

        PaymentResponseDTO payment = service.updatePaymentStatus(1, "SUCCESS");

        assertEquals("SUCCESS", payment.getStatus());
    }
}