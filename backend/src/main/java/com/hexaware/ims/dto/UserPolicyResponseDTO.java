package com.hexaware.ims.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPolicyResponseDTO {

    private int userPolicyId;
    private int proposalId;
    private String status;

}