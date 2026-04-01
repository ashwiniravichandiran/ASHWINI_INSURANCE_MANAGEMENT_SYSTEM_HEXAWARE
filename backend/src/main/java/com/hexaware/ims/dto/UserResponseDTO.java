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
public class UserResponseDTO {

    private int userId;
    private String name;
    private String email;
    private String role;
    private String address;
    private LocalDate dob;

}