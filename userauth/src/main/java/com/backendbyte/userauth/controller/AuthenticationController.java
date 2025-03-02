package com.backendbyte.userauth.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backendbyte.userauth.dto.LoginRequestDTO;
import com.backendbyte.userauth.dto.LoginResponseDTO;
import com.backendbyte.userauth.entity.User;
import com.backendbyte.userauth.exception.BadCredentialException;
import com.backendbyte.userauth.exception.EntityNotSaveException;
import com.backendbyte.userauth.serviceImpl.LoginServiceImpl;
import com.backendbyte.userauth.serviceImpl.UserServiceImpl;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	private final UserServiceImpl userService;
	private final LoginServiceImpl loginService;

	public AuthenticationController(UserServiceImpl userService, LoginServiceImpl loginService) {
		super();
		this.userService = userService;
		this.loginService = loginService;
	}

	@SuppressWarnings("null")
	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody User user) {
		if (user == null && !user.getUsername().matches("^[a-zA-Z][a-zA-Z0-9]*$")) {
			throw new RuntimeException("Username should have only alphabets or alphabets with number.");
		}
		try {
			logger.info("Request to save user: {}", user);
			userService.save(user);
			return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
		} catch (EntityNotSaveException ex) {
			logger.error("Failed to save user: {}", ex.getMessage(), ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponseDTO> loginUser(@RequestBody LoginRequestDTO loginRequestDto) {
		logger.info("Login request receive, verifing user: {}", loginRequestDto.getUsername());
		try {
			String token = loginService.verifyLogin(loginRequestDto);
			LoginResponseDTO response = new LoginResponseDTO();
			response.setUsername(loginRequestDto.getUsername());
			response.setToken(token);
			System.out.println("Response: "+response.toString());
			return new ResponseEntity<>(response, HttpStatus.OK);
			
		} catch (BadCredentialException e) {
            logger.error("Bad credentials for user: {}", loginRequestDto.getUsername(), e);
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            logger.error("Error during login for user: {}", loginRequestDto.getUsername(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
	}
}
