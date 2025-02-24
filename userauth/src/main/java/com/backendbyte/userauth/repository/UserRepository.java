package com.backendbyte.userauth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backendbyte.userauth.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

}
