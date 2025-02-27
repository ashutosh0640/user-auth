package com.backendbyte.userauth.dto;

import java.util.Set;

import com.backendbyte.userauth.entity.Profile;

public class UserDTO {

	private Long id;
	
	private String username;
	
	private Set<String> roles;
	
	public UserDTO() {}

	public UserDTO(Long id, String username, Set<String> roles) {
		super();
		this.id = id;
		this.username = username;
		this.roles = roles;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}	



	@Override
	public String toString() {
		return "UserDTO [id=" + id + ", username=" + username + ", roles=" + roles + "]";
	}

}
