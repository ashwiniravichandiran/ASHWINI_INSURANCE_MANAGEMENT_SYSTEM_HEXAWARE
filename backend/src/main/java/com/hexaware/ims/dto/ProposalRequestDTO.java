package com.hexaware.ims.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProposalRequestDTO {

    private int policyId;
    private String vehicleType;
    private String vehicleNumber;
    private String vehicleModel;
    private boolean zeroDep;
    private boolean rsa;
    private boolean paCover;
    private int duration;

}