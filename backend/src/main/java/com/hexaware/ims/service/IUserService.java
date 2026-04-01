package com.hexaware.ims.service;

import java.util.List;

import com.hexaware.ims.dto.UserRequestDTO;
import com.hexaware.ims.dto.UserResponseDTO;

public interface IUserService {

	UserResponseDTO addUser(UserRequestDTO userdto);

	UserResponseDTO getUserById(int id);

	List<UserResponseDTO> ViewAllUsers();

	List<UserResponseDTO> ViewByRole(String role);
	
	UserResponseDTO getUserByEmail(String email);

}