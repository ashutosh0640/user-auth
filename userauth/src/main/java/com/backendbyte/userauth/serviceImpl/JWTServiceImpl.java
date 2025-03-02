package com.backendbyte.userauth.serviceImpl;

import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.backendbyte.userauth.config.JWTConfig;
import com.backendbyte.userauth.service.JWTService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTServiceImpl implements JWTService {

	private final JWTConfig jwtConfig;
	private final UserDetailsService userDetailsService; // Use UserDetailsService instead of UserServiceImpl
	private static final Logger logger = LoggerFactory.getLogger(JWTServiceImpl.class);

	public JWTServiceImpl(JWTConfig jwtConfig, UserDetailsService userDetailsService) {
		this.jwtConfig = jwtConfig;
		this.userDetailsService = userDetailsService;
	}

	@Override
	public String generateToken(String username) {
		logger.info("Generating JWT token for user: {}", username);
		System.out.println("Jwt config: "+jwtConfig.getAlgorithm()+" "+jwtConfig.getExpiration()+" "+jwtConfig.getSecretKey());

		if (username == null || username.trim().isEmpty()) {
			logger.error("Username cannot be null or empty");
			throw new IllegalArgumentException("Username cannot be null or empty");
		}

		try {
			// Calculate token expiration date
			Date expirationDate = calculateExpirationDate();

			// Fetch user authorities
			Collection<? extends GrantedAuthority> authorities = fetchUserAuthorities(username);

			// Build JWT claims
			Map<String, Object> claims = buildClaims(username, authorities);

			// Generate and return the JWT token
			return buildJwtToken(claims, expirationDate);
		} catch (Exception e) {
			logger.error("Failed to generate JWT token for user: {}", username, e);
			throw new RuntimeException("Failed to generate JWT token", e);
		}
	}

	/**
	 * Calculates the token expiration date based on the configured expiration time.
	 *
	 * @return The expiration date.
	 */
	private Date calculateExpirationDate() {
		String expirationString = jwtConfig.getExpiration();
		try {
			long expirationSeconds = Long.parseLong(expirationString); // Parse the string
			Instant expirationInstant = Instant.now().plusSeconds(expirationSeconds);
			return Date.from(expirationInstant);
		} catch (NumberFormatException e) {
			logger.error("Invalid expiration time configuration: {}", expirationString, e);
			throw new IllegalArgumentException("Invalid expiration time configuration", e);
		}
	}

	/**
	 * Fetches the authorities (roles) for the given username.
	 *
	 * @param username The username.
	 * @return A collection of granted authorities.
	 */
	private Collection<? extends GrantedAuthority> fetchUserAuthorities(String username) {
		try {
			return userDetailsService.loadUserByUsername(username).getAuthorities();
		} catch (UsernameNotFoundException e) {
			logger.error("User not found: {}", username, e);
			throw new RuntimeException("User not found: " + username, e);
		}
	}

	/**
	 * Builds the JWT claims.
	 *
	 * @param username    The username.
	 * @param authorities The user authorities.
	 * @return A map of claims.
	 */
	private Map<String, Object> buildClaims(String username, Collection<? extends GrantedAuthority> authorities) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("sub", username); // Standard claim: subject
		claims.put("authorities", authorities); // Custom claim: authorities
		return claims;
	}

	/**
	 * Builds the JWT token.
	 *
	 * @param claims         The JWT claims.
	 * @param expirationDate The token expiration date.
	 * @return The generated JWT token.
	 */
	private String buildJwtToken(Map<String, Object> claims, Date expirationDate) {
		return Jwts.builder()
				.claims(claims)
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(expirationDate)
				.signWith(getKey())
				.compact();
	}

	public String extractUsernameFromToken(String token) {
		logger.info("Extracting username from token");
		return getClaimsFromToken(token, Claims::getSubject);
	}

	/**
	 * Retrieves the secret key for signing the JWT.
	 *
	 * @return The SecretKey object.
	 * @throws IllegalArgumentException If the secret key is invalid.
	 */
	private SecretKey getKey() {
		try {
			byte[] keyBytes = Decoders.BASE64.decode(jwtConfig.getSecretKey());
			if (keyBytes.length < 32) { // Ensure key length is sufficient for HMAC-SHA256
				throw new IllegalArgumentException("Secret key must be at least 256 bits (32 bytes)");
			}
			return Keys.hmacShaKeyFor(keyBytes);
		} catch (Exception e) {
			logger.error("Failed to decode or create secret key: {}", e.getMessage(), e);
			throw new IllegalArgumentException("Invalid secret key", e);
		}
	}

	/**
	 * Extracts a specific claim from the JWT token.
	 *
	 * @param token         The JWT token.
	 * @param claimResolver A function to resolve the claim.
	 * @return The resolved claim.
	 */
	private <T> T getClaimsFromToken(String token, Function<Claims, T> claimResolver) {
		final Claims claims = extractAllClaims(token);
		return claimResolver.apply(claims);
	}

	/**
	 * Extracts all claims from the JWT token.
	 *
	 * @param token The JWT token.
	 * @return The Claims object.
	 * @throws SecurityException If the token is invalid or tampered with.
	 */
	private Claims extractAllClaims(String token) {
		try {
			return Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token).getPayload();
		} catch (ExpiredJwtException e) {
			logger.error("JWT token is expired: {}", e.getMessage(), e);
			throw new SecurityException("JWT token is expired", e);
		} catch (MalformedJwtException e) {
			logger.error("JWT token is malformed: {}", e.getMessage(), e);
			throw new SecurityException("JWT token is malformed", e);
		} catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsupported: {}", e.getMessage(), e);
			throw new SecurityException("JWT token is unsupported", e);
		} catch (IllegalArgumentException e) {
			logger.error("JWT token is invalid: {}", e.getMessage(), e);
			throw new SecurityException("JWT token is invalid", e);
		}
	}
	
	/**
	 * Validate the JWT token.
	 * 
	 * @param token The JWT token.
	 * @param userDetails
	 * @return True if the token is validated.
	 */

	public boolean validateToken(String token, UserDetails userDetails) {
		logger.info("Validating JWT token for user: {}", userDetails.getUsername());

		try {
			final String username = extractUsernameFromToken(token);
			return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
		} catch (Exception e) {
			logger.error("Failed to validate JWT token: {}", e.getMessage(), e);
			return false;
		}
	}

	/**
	 * Checks if the JWT token is expired.
	 *
	 * @param token The JWT token.
	 * @return True if the token is expired, false otherwise.
	 */
	private boolean isTokenExpired(String token) {
		return tokenExpireDate(token).before(new Date());
	}

	/**
	 * Extracts the expiration date from the JWT token.
	 *
	 * @param token The JWT token.
	 * @return The expiration date.
	 */
	private Date tokenExpireDate(String token) {
		return getClaimsFromToken(token, Claims::getExpiration);
	}

}
