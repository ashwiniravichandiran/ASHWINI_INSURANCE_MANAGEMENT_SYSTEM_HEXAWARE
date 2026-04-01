package com.hexaware.ims.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponseDTO {

    private int paymentId;
    private int proposalId;
    private double amount;
    private LocalDateTime paymentDate;
    private String status;

}