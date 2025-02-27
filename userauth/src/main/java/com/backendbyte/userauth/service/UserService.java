package com.backendbyte.userauth.service;

import java.util.List;

import com.backendbyte.userauth.dto.UserDTO;
import com.backendbyte.userauth.dto.UserProfileDTO;
import com.backendbyte.userauth.entity.User;

public interface UserService {

	public List<UserDTO> saveAll(Iterable<User> roles);

	public List<UserDTO> findAll();

	public List<UserDTO> findAllById(Iterable<Long> ids);

	public <S extends UserDTO> S save(User entity);

	public UserDTO findById(Long id);

	public boolean existsById(Long id);

	public long count();

	public void deleteById(Long id);

	public void delete(User role);

	public void deleteAllById(Iterable<? extends Long> ids);

	public void deleteAll(Iterable<? extends User> entities);

	public void deleteAll();

	// ============= custom methods =============//
	public UserDTO updateUser(Long id, User newUser);
	
	public UserProfileDTO findByUsername(String username);

	public UserProfileDTO findByEmail(String email);

	public UserProfileDTO findByMobileNo(String mobileNo);


}
