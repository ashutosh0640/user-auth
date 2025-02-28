package com.backendbyte.userauth.entity;

import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private Set<GrantedAuthority> authorities;

	public CustomUserDetails() {
		super();
	}

	public CustomUserDetails(String username, String password, Set<GrantedAuthority> authorities) {
		this.username = username;
		this.password = password;
		this.authorities = authorities;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Set<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	@Override
	public String toString() {
		return "CustomUserDetails [username=" + username + ", password=" + password + ", authorities=" + authorities
				+ "]";
	}
	
	

}
