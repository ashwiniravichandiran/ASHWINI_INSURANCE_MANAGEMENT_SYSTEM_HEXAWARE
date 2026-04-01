package com.hexaware.ims.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name = "policies")
public class Policy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int policyId;

    @Column(unique = true)
    private String policyName;
    private String description;
    private double basePremium;
    private String vehicleType;

    @OneToMany(mappedBy = "policy")
    private List<Proposal> proposals;

    public Policy(String policy_name, String description, double base_premium) {
        this.policyName = policy_name;
        this.description = description;
        this.basePremium = base_premium;
    }

}