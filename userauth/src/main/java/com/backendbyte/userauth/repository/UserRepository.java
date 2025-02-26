package com.backendbyte.userauth.repository;

import java.util.List;
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
	User findByEmail(@Param("email") String email);

	// Custom JPQL Query to find user by mobile number (Joining Profile)
	@Query("SELECT u FROM User u JOIN u.profile p WHERE p.mobileNo = :mobileNo")
	User findByMobileNo(@Param("mobileNo") String mobileNo);

	@Query("SELECT u.id AS userId, u.username AS username, "
			+ "p.firstName AS firstName, p.middleName AS middleName, p.lastName AS lastName, "
			+ "p.email AS email, p.mobileNo AS mobileNo, p.lastLogin AS lastLoginDate, p.image AS image, "
			+ "r.roleName AS roleName " + "FROM User u " + "LEFT JOIN u.profile p " + "LEFT JOIN u.roles r "
			+ "WHERE u.username = :username")
	List<Object[]> findUserDetailsByUsername(@Param("username") String username);

}
