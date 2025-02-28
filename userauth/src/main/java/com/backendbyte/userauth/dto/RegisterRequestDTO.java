package com.backendbyte.userauth.dto;

import java.util.Set;

public class RegisterRequestDTO {

	private String username;
	private String password;
	private Set<String> roles;

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public Set<String> getRoles() {
		return roles;
	}
	
	

}
