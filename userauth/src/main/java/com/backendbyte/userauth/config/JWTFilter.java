package com.backendbyte.userauth.config;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.backendbyte.userauth.serviceImpl.CustomUserDetailsService;
import com.backendbyte.userauth.serviceImpl.JWTServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTFilter extends OncePerRequestFilter {

	private final Logger logger;
	private final JWTServiceImpl jwtService;
	private final CustomUserDetailsService userDetailsService;

	public JWTFilter(JWTServiceImpl jwtService, CustomUserDetailsService userDetailsService) {
		super();
		this.logger = LoggerFactory.getLogger(JWTFilter.class);
		this.jwtService = jwtService;
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		System.out.println("request: "+request.toString());
		try {
			String authHeader = request.getHeader("Authorization");
			String jwtToken = null;
			String username = null;

			if (authHeader != null && authHeader.startsWith("Bearer ")) {
				jwtToken = authHeader.substring(7);
				logger.debug("JWT token extracted from Authorization header.");

				username = jwtService.extractUsernameFromToken(jwtToken);
				logger.debug("Username extracted from JWT token: {}", username);

				if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
					logger.debug("Username is not null and SecurityContext is empty.");

					UserDetails userDetails = userDetailsService.loadUserByUsername(username);

					logger.debug("User details loaded for username: {}", username);

					if (jwtService.validateToken(jwtToken, userDetails)) {

						logger.debug("JWT token validated successfully.");

						UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
								userDetails, null, userDetails.getAuthorities());
						authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
						SecurityContextHolder.getContext().setAuthentication(authenticationToken);
					}
				}
			}
		} catch (UsernameNotFoundException e) {
			logger.error("User not found: {}", e.getMessage(), e);
		} catch (Exception e) {
			logger.error("Error processing JWT token: {}", e.getMessage(), e);
		}
		filterChain.doFilter(request, response);
	}

}
