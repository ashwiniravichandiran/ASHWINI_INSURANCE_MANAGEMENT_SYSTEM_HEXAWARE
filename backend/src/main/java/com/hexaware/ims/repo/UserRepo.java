package com.hexaware.ims.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hexaware.ims.entity.User;

public interface UserRepo extends JpaRepository<User,Integer>{
	Optional<User> findByEmail(String email);
	
	@Query("select u from User u where u.role = ?1")
	List<User> viewByRole(String role);
	
	
}
