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

import com.backendbyte.userauth.dto.UserDTO;
import com.backendbyte.userauth.entity.User;
import com.backendbyte.userauth.exception.EntityNotFoundException;
import com.backendbyte.userauth.exception.EntityNotSaveException;
import com.backendbyte.userauth.serviceImpl.UserServiceImpl;

// import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/user")
// @Tag(name = "User Controller", description = "APIs for user management")
public class UserController {
	
	private final Logger logger;
	private final UserServiceImpl userService;
	
	public UserController(UserServiceImpl userService) {
		super();
		this.logger = LoggerFactory.getLogger(UserController.class);
		this.userService = userService;
	}

	// Create multiple users
    @PostMapping("/bulk")
    public ResponseEntity<List<UserDTO>> saveAllUsers(@RequestBody List<User> users) {
        try {
            logger.info("Request to save multiple users: {}", users);
            List<UserDTO> savedUsers = userService.saveAll(users);
            return new ResponseEntity<>(savedUsers, HttpStatus.CREATED);
        } catch (EntityNotSaveException ex) {
            logger.error("Failed to save users: {}", ex.getMessage(), ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get all users
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        try {
            logger.info("Request to fetch all users");
            List<UserDTO> users = userService.findAll();
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Failed to fetch all users: {}", ex.getMessage(), ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get users by IDs
    @GetMapping("/ids")
    public ResponseEntity<List<UserDTO>> getUsersByIds(@RequestParam List<Long> ids) {
        try {
            logger.info("Request to fetch users by IDs: {}", ids);
            List<UserDTO> users = userService.findAllById(ids);
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Failed to fetch users by IDs: {}", ex.getMessage(), ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Create a single user
    @PostMapping
    public ResponseEntity<UserDTO> saveUser(@RequestBody User user) {
        try {
            logger.info("Request to save user: {}", user);
            UserDTO savedUser = userService.save(user);
            return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
        } catch (EntityNotSaveException ex) {
            logger.error("Failed to save user: {}", ex.getMessage(), ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        try {
            logger.info("Request to fetch user by ID: {}", id);
            UserDTO user = userService.findById(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Failed to fetch user by ID: {}", ex.getMessage(), ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Check if user exists by ID
    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> existsById(@PathVariable Long id) {
        try {
            logger.info("Request to check if user exists by ID: {}", id);
            boolean exists = userService.existsById(id);
            return new ResponseEntity<>(exists, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Failed to check if user exists by ID: {}", ex.getMessage(), ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get total count of users
    @GetMapping("/count")
    public ResponseEntity<Long> getUserCount() {
        try {
            logger.info("Request to count users");
            long count = userService.count();
            return new ResponseEntity<>(count, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Failed to count users: {}", ex.getMessage(), ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete user by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        try {
            logger.info("Request to delete user by ID: {}", id);
            userService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ex) {
            logger.error("Failed to delete user by ID: {}", ex.getMessage(), ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete a user
    @DeleteMapping
    public ResponseEntity<Void> deleteUser(@RequestBody User user) {
        try {
            logger.info("Request to delete user: {}", user);
            userService.delete(user);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ex) {
            logger.error("Failed to delete user: {}", ex.getMessage(), ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete users by IDs
    @DeleteMapping("/bulk")
    public ResponseEntity<Void> deleteUsersByIds(@RequestParam List<Long> ids) {
        try {
            logger.info("Request to delete users by IDs: {}", ids);
            userService.deleteAllById(ids);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ex) {
            logger.error("Failed to delete users by IDs: {}", ex.getMessage(), ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete all users
    @DeleteMapping("/all")
    public ResponseEntity<Void> deleteAllUsers() {
        try {
            logger.info("Request to delete all users");
            userService.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ex) {
            logger.error("Failed to delete all users: {}", ex.getMessage(), ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update user by ID
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody User newUser) {
        try {
            logger.info("Request to update user with ID: {}", id);
            UserDTO updatedUser = userService.updateUser(id, newUser);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (EntityNotFoundException ex) {
            logger.error("User not found with ID: {}", id, ex);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            logger.error("Failed to update user with ID: {}", id, ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/{username}")
    public ResponseEntity<UserDTO> findByUsername(@PathVariable String username) {
		return null;
    }

}
