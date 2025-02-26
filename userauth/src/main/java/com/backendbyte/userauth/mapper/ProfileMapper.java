package com.backendbyte.userauth.mapper;

import com.backendbyte.userauth.dto.ProfileDTO;
import com.backendbyte.userauth.entity.Profile;

public class ProfileMapper {
	
	public static ProfileDTO toDTO(Profile profile) {
		if (profile == null) {
			return null;
		}

		ProfileDTO profileDTO = new ProfileDTO();
		profileDTO.setId(profile.getId());
		profileDTO.setFirstName(profile.getFirstName());
		profileDTO.setMiddleName(profile.getMiddleName());
		profileDTO.setLastName(profile.getLastName());
		profileDTO.setEmail(profile.getEmail());
		profileDTO.setMobileNo(profile.getMobileNo());
		profileDTO.setImage(profile.getImage());
		profileDTO.setLastLogin(profile.getLastLogin());
		profileDTO.setUsername(profile.getUser().getUsername());
		return profileDTO;
	}
}
