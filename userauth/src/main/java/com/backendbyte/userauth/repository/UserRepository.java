package com.backendbyte.userauth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.backendbyte.userauth.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String username);

	// Custom JPQL Query to find user by email (Joining Profile)
	@Query("SELECT u FROM User u JOIN u.profile p WHERE p.email = :email")
	Optional<User> findByEmail(@Param("email") String email);

	// Custom JPQL Query to find user by mobile number (Joining Profile)
	@Query("SELECT u FROM User u JOIN u.profile p WHERE p.mobileNo = :mobileNo")
	Optional<User> findByMobileNo(@Param("mobileNo") String mobileNo);
}
