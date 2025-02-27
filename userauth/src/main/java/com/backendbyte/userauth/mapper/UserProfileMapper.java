package com.backendbyte.userauth.mapper;

import java.util.HashSet;
import java.util.Set;

import com.backendbyte.userauth.dto.UserProfileDTO;
import com.backendbyte.userauth.entity.User;

public class UserProfileMapper {
	
	public static UserProfileDTO toDTO(User user) {
		UserProfileDTO userProfile = new UserProfileDTO();
		userProfile.setUserId(user.getId());
		userProfile.setUsername(user.getUsername());
		userProfile.setFirstName(user.getProfile().getFirstName());
		userProfile.setMiddleName(user.getProfile().getMiddleName());
		userProfile.setLastName(user.getProfile().getLastName());
		userProfile.setEmail(user.getProfile().getEmail());
		userProfile.setMobileNo(user.getProfile().getMobileNo());
		userProfile.setImage(user.getProfile().getImage());
		
		Set<String> roles = new HashSet<>();
		user.getRoles().forEach(role -> roles.add(role.getRoleName()));
		userProfile.setRoles(roles);
		return userProfile;
	}

}
