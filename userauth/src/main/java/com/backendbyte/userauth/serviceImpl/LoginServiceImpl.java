package com.backendbyte.userauth.serviceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.backendbyte.userauth.dto.LoginRequestDTO;
import com.backendbyte.userauth.exception.BadCredentialException;
import com.backendbyte.userauth.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService{
	
	private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);
	
	private final JWTServiceImpl jwtService;
	private final AuthenticationManager authManager;
	
	public LoginServiceImpl(JWTServiceImpl jwtService, AuthenticationManager authManager) {
		super();
		this.jwtService = jwtService;
		this.authManager = authManager;
	}




	@Override
	public String verifyLogin(LoginRequestDTO loginDto) {
		logger.info("Verifying user: {}",loginDto.getUsername());
		
		try {
			
			Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
			
			if (!authentication.isAuthenticated()) {
				logger.error("Authentication failed for user: {}", loginDto.getUsername());
				throw new BadCredentialException("Bad credentials.");
			}
			
			SecurityContextHolder.getContext().setAuthentication(authentication);
			logger.info("User authenticated successfully: {}", loginDto.getUsername());
			
			
		
		} catch (BadCredentialException e) {
			logger.error("Bad credentials for user: {}", loginDto.getUsername(), e);
			throw e;
		} catch (Exception e) {
			logger.error("Unexpected login error fro user: {}", loginDto.getUsername(), e);
		}
		
		return jwtService.generateToken(loginDto.getUsername());
		
	}
}
