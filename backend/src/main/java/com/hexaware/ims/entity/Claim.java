package com.hexaware.ims.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "claims")
public class Claim {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int claimId;

    @ManyToOne
    @JoinColumn(name = "user_policy_id")
    private UserPolicy userPolicy;

    private double claimAmount;
    private String reason;
    private String status;
    private LocalDateTime createdDate;

    public Claim(UserPolicy userPolicy, double claimAmount, String reason, String status) {
        this.userPolicy = userPolicy;
        this.claimAmount = claimAmount;
        this.reason = reason;
        this.status = status;
    }

}