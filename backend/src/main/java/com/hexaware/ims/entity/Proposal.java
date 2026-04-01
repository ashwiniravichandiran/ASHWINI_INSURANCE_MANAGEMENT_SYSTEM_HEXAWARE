package com.hexaware.ims.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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

@Table(name ="proposals")
public class Proposal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int proposalId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "policy_id")
    private Policy policy;
    
    @OneToOne(mappedBy = "proposal")
    private UserPolicy userPolicy;

    private String vehicleType;
    @Column(unique = true)
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
    
	public Proposal(User user, Policy policy, String vehicle_type, String vehicle_number, String vehicle_model,
			double premium_amount, LocalDateTime created_date, String status, boolean zeroDep, boolean rsa, 
			boolean paCover, LocalDateTime expiry, double basePremium, double addOnAmount, double gstAmount) {
		super();
		this.user = user;
		this.policy = policy;
		this.vehicleType = vehicle_type;
		this.vehicleNumber = vehicle_number;
		this.vehicleModel = vehicle_model;
		this.premiumAmount = premium_amount;
		this.createdDate = created_date;
		this.status = status;
		this.zeroDep=zeroDep;
		this.rsa = rsa;
		this.paCover=paCover;
		this.expiry=expiry;
	}

    
    
}