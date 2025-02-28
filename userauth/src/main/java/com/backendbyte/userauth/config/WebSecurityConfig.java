package com.backendbyte.userauth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.core.userdetails.User;
// import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	
	private UserDetailsService userDetailsService;
	
	public WebSecurityConfig(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Bean
 	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
 		http
 			.csrf(csrf -> csrf.disable())
 			.cors(cors -> cors.disable())
 			.authorizeHttpRequests((authorizeHttpRequests) ->
 				authorizeHttpRequests
 					.requestMatchers("/auth/**", "/swagger-ui/**").permitAll()
 					.requestMatchers("/api/**").hasRole("USER")
 			)
 			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
 			//.formLogin(Customizer.withDefaults());
 			.httpBasic(Customizer.withDefaults());
 		
 		return http.build();
 	}

// 	@Bean
// 	public UserDetailsService userDetailsService() {
// 		UserDetails user = User.withUsername("user")
// 			.password("{noop}password")
// 			.roles("USER")
// 			.build();
// 		UserDetails admin = User.withUsername("admin")
// 			.password(passwordEncoder().encode("password"))
// 			.roles("ADMIN", "USER")
// 			.build();
// 		return new InMemoryUserDetailsManager(user, admin);
// 	}
// 	
 	
 	@Bean
 	public PasswordEncoder passwordEncoder() {
 		return new BCryptPasswordEncoder();
 	}
 	
 	@Bean
 	public AuthenticationProvider authenticationProvider() {
 		DaoAuthenticationProvider daoProvider = new DaoAuthenticationProvider();
 		daoProvider.setUserDetailsService(userDetailsService);
 		daoProvider.setPasswordEncoder(passwordEncoder());
 		return daoProvider;
 	}
}


