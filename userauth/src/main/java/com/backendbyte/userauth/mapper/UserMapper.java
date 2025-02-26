package com.backendbyte.userauth.mapper;


import java.util.Set;
import java.util.stream.Collectors;

import com.backendbyte.userauth.dto.UserDTO;
import com.backendbyte.userauth.entity.User;

public class UserMapper {

	public static UserDTO toDTO(User user) {
		if (user == null) {
			return null;
		}

		UserDTO userDTO = new UserDTO();
		userDTO.setId(user.getId());
		userDTO.setUsername(user.getUsername());
		
		Set<String> roles = user.getRoles().stream()
		        .map(role -> role.getRoleName())
		        .collect(Collectors.toSet());
		
		if (user.getRoles() != null) {
			userDTO.setRoles(roles);
		}

		if (user.getProfile() != null) {
			userDTO.setProfile(user.getProfile());
		}

		return userDTO;
	}

}
