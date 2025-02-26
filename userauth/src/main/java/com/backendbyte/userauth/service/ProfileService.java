package com.backendbyte.userauth.service;

import java.util.List;

import com.backendbyte.userauth.dto.ProfileDTO;
import com.backendbyte.userauth.entity.Profile;

public interface ProfileService {

	public List<ProfileDTO> saveAll(Iterable<Profile> roles);

	public List<ProfileDTO> findAll();

	public List<ProfileDTO> findAllById(Iterable<Long> ids);

	public <S extends Profile> S save(S entity);

	public ProfileDTO findById(Long id);

	public boolean existsById(Long id);

	public long count();

	public void deleteById(Long id);

	public void delete(Profile role);

	public void deleteAllById(Iterable<? extends Long> ids);

	public void deleteAll(Iterable<? extends Profile> entities);

	public void deleteAll();

	// ============= custom methods =============//
	public ProfileDTO updateProfile(Long id, Profile newProfile);

}
