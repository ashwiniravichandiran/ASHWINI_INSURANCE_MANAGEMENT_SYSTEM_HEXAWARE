package com.hexaware.ims.service;

import java.util.List;

import com.hexaware.ims.dto.PaymentRequestDTO;
import com.hexaware.ims.dto.PaymentResponseDTO;

public interface IPaymentService {

    PaymentResponseDTO makePayment(PaymentRequestDTO dto);

    PaymentResponseDTO getPaymentById(int id);

    List<PaymentResponseDTO> getPaymentsByProposal(int proposalId);

    PaymentResponseDTO updatePaymentStatus(int paymentId, String status);

    List<PaymentResponseDTO> getPaymentsByUser(int userId);
}