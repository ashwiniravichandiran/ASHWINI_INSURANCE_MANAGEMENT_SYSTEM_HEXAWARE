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
public class ProposalResponseDTO {

    private int proposalId;
    private int userId;
    private int userPolicyId;
    private String userName;
    private int policyId;
    private String vehicleType;
    private String vehicleNumber;
    private String vehicleModel;
    private double premiumAmount;
    private LocalDateTime createdDate;
    private String status;
    private boolean zeroDep;
    private boolean rsa;
    private boolean paCover;
    private LocalDateTime expiry;
    private double basePremium;
    private double addOnAmount;
    private double gstAmount;

}