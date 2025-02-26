package com.backendbyte.userauth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backendbyte.userauth.entity.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long>{

}
