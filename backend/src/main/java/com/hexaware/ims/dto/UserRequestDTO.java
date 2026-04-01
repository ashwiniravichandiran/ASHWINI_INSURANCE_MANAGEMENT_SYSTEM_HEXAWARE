package com.hexaware.ims.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {

    private String name;
    private String email;
    private String password;
    private String role;
    private String address;
    private String aadhar;
    private String pan;
    private LocalDate dob;

}