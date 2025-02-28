package com.backendbyte.userauth.serviceImpl;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.backendbyte.userauth.entity.CustomUserDetails;
import com.backendbyte.userauth.entity.User;
import com.backendbyte.userauth.repository.UserRepository;


@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	private UserRepository userRepo;

	public CustomUserDetailsService(UserRepository userRepo) {
		super();
		this.userRepo = userRepo;
	}



	@Override
	public UserDetails loadUserByUsername(String loginAttribute) throws UsernameNotFoundException {
		
	    if (loginAttribute == null || loginAttribute.trim().isEmpty()) {
	        throw new RuntimeException("Login attribute cannot be null or empty");
	    }
		
	    User user;

	    // Check if loginAttribute is a mobile number (digits only)
	    if (loginAttribute.matches("\\d{10}")) { // Assuming a 10-digit mobile number
	        user = userRepo.findByMobileNo(loginAttribute)
	                .orElseThrow(() -> new UsernameNotFoundException("User not found with mobile number: " + loginAttribute));
	    }
	    // Check if loginAttribute is an email (contains '@')
	    else if (loginAttribute.contains("@")) {
	        user = userRepo.findByEmail(loginAttribute)
	                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + loginAttribute));
	    }
	    // Check if loginAttribute is a valid username (only alphabets or alphanumeric)
	    else if (loginAttribute.matches("^[a-zA-Z][a-zA-Z0-9]*$")) {
	        user = userRepo.findByUsername(loginAttribute)
	                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + loginAttribute));
	    }
	    else {
	        throw new UsernameNotFoundException("Invalid username format");
	    }
	    
	    
	    Set<GrantedAuthority> authorities = user.getRoles().stream()
	            .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRoleName()))
	            .collect(Collectors.toSet());
	    
	    return new CustomUserDetails(loginAttribute, user.getPassword(), authorities);

//	    return new org.springframework.security.core.userdetails.User(
//	            user.getUsername(),
//	            user.getPassword(),
//	            authorities
//	    );
	}
}
