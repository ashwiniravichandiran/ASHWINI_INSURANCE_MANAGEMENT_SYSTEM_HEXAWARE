package com.hexaware.ims.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.ims.dto.PaymentRequestDTO;
import com.hexaware.ims.dto.PaymentResponseDTO;
import com.hexaware.ims.service.IPaymentService;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    IPaymentService service;

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/add")
    public PaymentResponseDTO makePayment(@RequestBody PaymentRequestDTO dto) {
        return service.makePayment(dto);
    }

    @GetMapping("/get/{id}")
    public PaymentResponseDTO getPaymentById(@PathVariable int id) {
        return service.getPaymentById(id);
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/proposal/{proposalId}")
    public List<PaymentResponseDTO> getPaymentsByProposal(@PathVariable int proposalId) {
        return service.getPaymentsByProposal(proposalId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/update/{paymentId}/{status}")
    public PaymentResponseDTO updatePaymentStatus(@PathVariable int paymentId,
                                                  @PathVariable String status) {
        return service.updatePaymentStatus(paymentId, status);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/user/{userId}")
    public List<PaymentResponseDTO> getPaymentsByUser(@PathVariable int userId) {
        return service.getPaymentsByUser(userId);
    }
}