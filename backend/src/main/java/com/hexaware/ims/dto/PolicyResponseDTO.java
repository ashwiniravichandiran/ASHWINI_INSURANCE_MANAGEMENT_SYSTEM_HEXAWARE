package com.hexaware.ims.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PolicyResponseDTO {

    private int policyId;
    private String policyName;
    private String description;
    private double basePremium;
    private String vehicleType;
}