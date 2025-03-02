package com.backendbyte.userauth.dto;

public class LoginResponseDTO {

	private String token;
	private String username;


	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	@Override
	public String toString() {
		return "LoginResponseDTO [token=" + token + ", username=" + username + "]";
	}

}
