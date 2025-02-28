package com.backendbyte.userauth.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backendbyte.userauth.dto.UserDTO;
import com.backendbyte.userauth.entity.User;
import com.backendbyte.userauth.exception.EntityNotSaveException;
import com.backendbyte.userauth.serviceImpl.UserServiceImpl;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
	
	private final Logger logger;
	private final UserServiceImpl userService;

	public AuthenticationController(UserServiceImpl userService) {
		super();
		this.logger = LoggerFactory.getLogger(UserController.class);
		this.userService = userService;
	}

	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody User user) {
		if (user == null && !user.getUsername().matches("^[a-zA-Z][a-zA-Z0-9]*$")) {
			throw new RuntimeException("Username should have only alphabets or alphabets with number.");
		}
		try {
			logger.info("Request to save user: {}", user);
			UserDTO savedUser = userService.save(user);
			return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
		} catch (EntityNotSaveException ex) {
			logger.error("Failed to save user: {}", ex.getMessage(), ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	

}
