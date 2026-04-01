package com.hexaware.ims.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.ims.dto.AuthRequest;
import com.hexaware.ims.dto.AuthResponse;
import com.hexaware.ims.dto.UserRequestDTO;
import com.hexaware.ims.dto.UserResponseDTO;
import com.hexaware.ims.security.JwtService;
import com.hexaware.ims.service.EmailService;
import com.hexaware.ims.service.IUserService;

@RestController
@CrossOrigin(origins = "http://localhost:3000") 
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    IUserService service;
    
    @Autowired
    AuthenticationManager authenticationManager;
   
    @Autowired
    JwtService jwtService;
    
    @Autowired
    private EmailService emailService;

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody UserRequestDTO user) {

        user.setRole("USER");

        UserResponseDTO savedUser = service.addUser(user);

        emailService.sendRegistrationEmail(savedUser.getEmail());

        String token = jwtService.generateToken(savedUser.getEmail());

        String role = savedUser.getRole();
        System.out.println(role);
        return ResponseEntity.ok(
            new AuthResponse(token, "Registration successful", role)
        );
    }
    
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/findbyid/{id}")
    public UserResponseDTO getUserById(@PathVariable int id) {
        return service.getUserById(id);
    }

    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request){

        org.springframework.security.core.Authentication authentication =
        authenticationManager.authenticate(

        new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()));

       
        if(authentication.isAuthenticated()){

        	String token = jwtService.generateToken(request.getEmail());
        	emailService.sendLoginSuccessEmail(request.getEmail());
        	UserResponseDTO user = service.getUserByEmail(request.getEmail());

            String role = user.getRole();
            return ResponseEntity.ok(
                new AuthResponse(token, "Login successful", role)
            );
        }
        
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Invalid login");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/show")
    public List<UserResponseDTO> viewAllUsers(){
        return service.ViewAllUsers();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/findbyrole/{role}")
    public List<UserResponseDTO> viewByRole(@PathVariable String role){
        return service.ViewByRole(role);
    }
}