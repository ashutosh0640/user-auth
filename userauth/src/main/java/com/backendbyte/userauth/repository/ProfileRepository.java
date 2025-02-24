package com.backendbyte.userauth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backendbyte.userauth.entity.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long>{

}
