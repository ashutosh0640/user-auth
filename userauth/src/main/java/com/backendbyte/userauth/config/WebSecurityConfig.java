package com.backendbyte.userauth.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
// import org.springframework.security.core.userdetails.User;
// import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity

public class WebSecurityConfig {
	private static final Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);
	private UserDetailsService userDetailsService;
	private JWTFilter jwtFilter;
	
	public WebSecurityConfig(UserDetailsService userDetailsService, JWTFilter jwtFilter) {
		this.userDetailsService = userDetailsService;
		this.jwtFilter = jwtFilter;
	}

	@Bean
 	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
 		http
 			.csrf(csrf -> csrf.disable())
 			.cors(cors -> cors.disable())
 			.authorizeHttpRequests((authorizeHttpRequests) ->
 				authorizeHttpRequests
 					.requestMatchers("/auth/**").permitAll()
 					.requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/webjars/**").permitAll()
 					.requestMatchers("/api/**").hasRole("USER")
 			)
 			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
 			.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
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
 	
 	@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) {
        try {
            logger.info("Creating AuthenticationManager bean...");
            return authenticationConfiguration.getAuthenticationManager();
        } catch (Exception e) {
            logger.error("Failed to create AuthenticationManager: {}", e.getMessage(), e);
            throw new AuthenticationException("Failed to create AuthenticationManager", e) {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;
            	
            };
        }
    }
}


