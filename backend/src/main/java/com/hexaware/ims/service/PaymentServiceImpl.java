package com.hexaware.ims.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.ims.dto.PaymentRequestDTO;
import com.hexaware.ims.dto.PaymentResponseDTO;
import com.hexaware.ims.entity.Payment;
import com.hexaware.ims.entity.Proposal;
import com.hexaware.ims.exception.PaymentNotFoundException;
import com.hexaware.ims.exception.ProposalNotFoundException;
import com.hexaware.ims.repo.PaymentRepo;
import com.hexaware.ims.repo.ProposalRepo;

@Service
public class PaymentServiceImpl implements IPaymentService {

    @Autowired
    PaymentRepo paymentRepo;

    @Autowired
    ProposalRepo proposalRepo;

    public Payment convertToEntity(PaymentRequestDTO dto, Proposal proposal) {

        Payment payment = new Payment();

        payment.setProposal(proposal);
        payment.setAmount(dto.getAmount());
        payment.setPaymentDate(LocalDateTime.now());
        payment.setStatus("SUCCESS");

        return payment;
    }

    public PaymentResponseDTO convertToDTO(Payment payment) {

        PaymentResponseDTO dto = new PaymentResponseDTO();

        dto.setPaymentId(payment.getPaymentId());
        dto.setProposalId(payment.getProposal().getProposalId());
        dto.setAmount(payment.getAmount());
        dto.setPaymentDate(payment.getPaymentDate());
        dto.setStatus(payment.getStatus());

        return dto;
    }

    @Override
    public PaymentResponseDTO makePayment(PaymentRequestDTO dto) {
    	
    	Proposal proposal = proposalRepo.findById(dto.getProposalId())
        		.orElseThrow(() -> new ProposalNotFoundException(
        				"Proposal not found with id "+ dto.getProposalId()));
    	
    	if(!proposal.getStatus().equals("APPROVED")) {
    	    throw new IllegalStateException("Payment allowed only for approved proposals");
    	}

        Payment payment = convertToEntity(dto, proposal);

        Payment saved = paymentRepo.save(payment);

        return convertToDTO(saved);
    }

    @Override
    public PaymentResponseDTO getPaymentById(int id) {

        Payment payment = paymentRepo.findById(id).orElseThrow(
        		() -> new PaymentNotFoundException(
        				"Payment not found with id "+id));

        return convertToDTO(payment);
    }

    @Override
    public List<PaymentResponseDTO> getPaymentsByProposal(int proposalId) {

        List<Payment> payments = paymentRepo.findByProposalProposalId(proposalId);

        return payments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PaymentResponseDTO updatePaymentStatus(int paymentId, String status) {

        Payment payment = paymentRepo.findById(paymentId).orElseThrow(
        		() -> new PaymentNotFoundException(
        				"Payment not found with id "+paymentId));

        payment.setStatus(status);

        Payment updated = paymentRepo.save(payment);

        return convertToDTO(updated);
    }

    @Override
    public List<PaymentResponseDTO> getPaymentsByUser(int userId) {

        List<Payment> payments = paymentRepo.findByProposalUserUserId(userId);

        return payments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}