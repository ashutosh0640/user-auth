package com.backendbyte.userauth.service;

import java.util.List;

import com.backendbyte.userauth.dto.RoleDTO;
import com.backendbyte.userauth.entity.Role;

public interface RoleService {
	
	public List<RoleDTO> saveAll(Iterable<Role> roles);
	
	public List<RoleDTO> findAll();
	
	public List<RoleDTO> findAllById(Iterable<Integer> ids);
	
	public <S extends Role> S save(S entity);
	
	public RoleDTO findById(Integer id);
	
	public boolean existsById(Integer id);
	
	public long count();
	
	public void deleteById(Integer id);
	
	public void delete(Role role);
	
	public void deleteAllById(Iterable<? extends Integer> ids);
	
	public void deleteAll(Iterable<? extends Role> entities);
	
	public void deleteAll();
	
	//============= custom methods =============//
	public RoleDTO updateRole(int id, Role newRole);

}
