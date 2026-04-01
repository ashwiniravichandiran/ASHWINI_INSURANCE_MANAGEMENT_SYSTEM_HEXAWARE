package com.hexaware.ims.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hexaware.ims.dto.UserRequestDTO;
import com.hexaware.ims.dto.UserResponseDTO;
import com.hexaware.ims.entity.User;
import com.hexaware.ims.exception.UserNotFoundException;
import com.hexaware.ims.repo.UserRepo;
import com.hexaware.security3.service.UserService;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	PasswordEncoder passwordEncoder;
	
    @Autowired
    UserRepo repo;
    
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public User convertToEntity(UserRequestDTO dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setRole(dto.getRole());
        user.setAddress(dto.getAddress());
        user.setAadhar(dto.getAadhar());
        user.setPan(dto.getPan());
        user.setDob(dto.getDob());
        return user;
    }

    public UserResponseDTO convertToDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setUserId(user.getUserId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setAddress(user.getAddress());
        dto.setDob(user.getDob());
        return dto;
    }

    @Override
    public UserResponseDTO addUser(UserRequestDTO userdto) {

    	logger.info("User registration started");
    	 
        User user = convertToEntity(userdto);
        
        user.setPassword(passwordEncoder.encode(userdto.getPassword()));
        
        User savedUser = repo.save(user);
        
        logger.info("User registered successfully with id {}", savedUser.getUserId());

        return convertToDTO(savedUser);
    }

    @Override
    public UserResponseDTO getUserById(int id) {

    	logger.debug("Fetching User details");
    	
        Optional<User> user = repo.findById(id);

        if(user.isPresent()) {
            return convertToDTO(user.get());
        }
        else {
        	throw new UserNotFoundException("User not found with id "+id);
        }

       
    }

    @Override
    public List<UserResponseDTO> ViewAllUsers() {

        return repo.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserResponseDTO> ViewByRole(String role) {

        return repo.viewByRole(role)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

	@Override
	public UserResponseDTO getUserByEmail(String email) {
		// TODO Auto-generated method stub
		
		Optional<User> user = repo.findByEmail(email);

        if(user.isPresent()) {
            return convertToDTO(user.get());
        }
        else {
        	throw new UserNotFoundException("User not found with email"+email);
        }
		
	}
}