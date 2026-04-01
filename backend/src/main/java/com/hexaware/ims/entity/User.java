package com.hexaware.ims.entity;



import java.time.LocalDate;
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

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "users")

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name = "user_id")
    private int userId;
	private String name;
	@Column(unique = true)
    private String email;
    private String password;
    private String role;
    private String address;
    @Column(unique = true)
    private String aadhar;

    @Column(unique = true)
    private String pan;
    private LocalDate dob;

    @OneToMany(mappedBy = "user")
    private List<Proposal> proposals;

    public User(String name, String email, String password, String role,
                String address, String aadhar, String pan, LocalDate dob) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.address = address;
        this.aadhar = aadhar;
        this.pan = pan;
        this.dob = dob;
    }

    
}