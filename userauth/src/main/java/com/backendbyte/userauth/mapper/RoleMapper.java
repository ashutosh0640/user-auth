package com.backendbyte.userauth.mapper;

import com.backendbyte.userauth.dto.RoleDTO;
import com.backendbyte.userauth.entity.Role;

public class RoleMapper {
	
	public static RoleDTO toDTO(Role role) {
		if (role == null) {
			return null;
		}

		RoleDTO roleDTO = new RoleDTO();
		roleDTO.setId(role.getId());
		roleDTO.setRoleName(role.getRoleName());
		
		return roleDTO;
	}
}
