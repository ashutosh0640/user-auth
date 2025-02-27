package com.backendbyte.userauth.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backendbyte.userauth.dto.ProfileDTO;
import com.backendbyte.userauth.entity.Profile;
import com.backendbyte.userauth.exception.EntityNotFoundException;
import com.backendbyte.userauth.exception.EntityNotSaveException;
import com.backendbyte.userauth.serviceImpl.ProfileServiceImpl;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {
	
	private final Logger logger;
	private final ProfileServiceImpl profileService;
	
	public ProfileController(ProfileServiceImpl profileService) {
		super();
		this.logger = LoggerFactory.getLogger(ProfileController.class);
		this.profileService = profileService;
	}

	// Create multiple profiles
    @PostMapping("/bulk")
    public ResponseEntity<List<ProfileDTO>> saveAllProfiles(@RequestBody List<Profile> profiles) {
        try {
            logger.info("Request to save multiple profiles: {}", profiles);
            List<ProfileDTO> savedProfiles = profileService.saveAll(profiles);
            return new ResponseEntity<>(savedProfiles, HttpStatus.CREATED);
        } catch (EntityNotSaveException ex) {
            logger.error("Failed to save profiles: {}", ex.getMessage(), ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get all profiles
    @GetMapping
    public ResponseEntity<List<ProfileDTO>> getAllProfiles() {
        try {
            logger.info("Request to fetch all profiles");
            List<ProfileDTO> profiles = profileService.findAll();
            return new ResponseEntity<>(profiles, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Failed to fetch all profiles: {}", ex.getMessage(), ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get profiles by IDs
    @GetMapping("/ids")
    public ResponseEntity<List<ProfileDTO>> getProfilesByIds(@RequestParam List<Long> ids) {
        try {
            logger.info("Request to fetch profiles by IDs: {}", ids);
            List<ProfileDTO> profiles = profileService.findAllById(ids);
            return new ResponseEntity<>(profiles, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Failed to fetch profiles by IDs: {}", ex.getMessage(), ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Create a single profile
    @PostMapping
    public ResponseEntity<Profile> saveProfile(@RequestBody Profile profile) {
        try {
            logger.info("Request to save profile: {}", profile);
            Profile savedProfile = profileService.save(profile);
            return new ResponseEntity<>(savedProfile, HttpStatus.CREATED);
        } catch (EntityNotSaveException ex) {
            logger.error("Failed to save profile: {}", ex.getMessage(), ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get profile by ID
    @GetMapping("/{id}")
    public ResponseEntity<ProfileDTO> getProfileById(@PathVariable Long id) {
        try {
            logger.info("Request to fetch profile by ID: {}", id);
            ProfileDTO profile = profileService.findById(id);
            return  new ResponseEntity<>(profile, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Failed to fetch profile by ID: {}", ex.getMessage(), ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Check if profile exists by ID
    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> existsById(@PathVariable Long id) {
        try {
            logger.info("Request to check if profile exists by ID: {}", id);
            boolean exists = profileService.existsById(id);
            return new ResponseEntity<>(exists, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Failed to check if profile exists by ID: {}", ex.getMessage(), ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get total count of profiles
    @GetMapping("/count")
    public ResponseEntity<Long> getProfileCount() {
        try {
            logger.info("Request to count profiles");
            long count = profileService.count();
            return new ResponseEntity<>(count, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Failed to count profiles: {}", ex.getMessage(), ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete profile by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfileById(@PathVariable Long id) {
        try {
            logger.info("Request to delete profile by ID: {}", id);
            profileService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ex) {
            logger.error("Failed to delete profile by ID: {}", ex.getMessage(), ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete a profile
    @DeleteMapping
    public ResponseEntity<Void> deleteProfile(@RequestBody Profile profile) {
        try {
            logger.info("Request to delete profile: {}", profile);
            profileService.delete(profile);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ex) {
            logger.error("Failed to delete profile: {}", ex.getMessage(), ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete profiles by IDs
    @DeleteMapping("/bulk")
    public ResponseEntity<Void> deleteProfilesByIds(@RequestParam List<Long> ids) {
        try {
            logger.info("Request to delete profiles by IDs: {}", ids);
            profileService.deleteAllById(ids);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ex) {
            logger.error("Failed to delete profiles by IDs: {}", ex.getMessage(), ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete all profiles
    @DeleteMapping("/all")
    public ResponseEntity<Void> deleteAllProfiles() {
        try {
            logger.info("Request to delete all profiles");
            profileService.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ex) {
            logger.error("Failed to delete all profiles: {}", ex.getMessage(), ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update profile by ID
    @PutMapping("/{id}")
    public ResponseEntity<ProfileDTO> updateProfile(@PathVariable Long id, @RequestBody Profile newProfile) {
        try {
            logger.info("Request to update profile with ID: {}", id);
            ProfileDTO updatedProfile = profileService.updateProfile(id, newProfile);
            return new ResponseEntity<>(updatedProfile, HttpStatus.OK);
        } catch (EntityNotFoundException ex) {
            logger.error("Profile not found with ID: {}", id, ex);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            logger.error("Failed to update profile with ID: {}", id, ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
