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
public class ClaimResponseDTO {

    private int claimId;
    private int userPolicyId;
    private double claimAmount;
    private String reason;
    private String status;
    private LocalDateTime createdDate;
    private String vehicleNumber;
    private String vehicleModel;

}