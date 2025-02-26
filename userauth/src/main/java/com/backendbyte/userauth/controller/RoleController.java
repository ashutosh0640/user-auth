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

import com.backendbyte.userauth.dto.RoleDTO;
import com.backendbyte.userauth.entity.Role;
import com.backendbyte.userauth.exception.EntityNotFoundException;
import com.backendbyte.userauth.exception.EntityNotSaveException;
import com.backendbyte.userauth.serviceImpl.RoleServiceImpl;

// import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/role")
// @Tag(name = "Role Controller", description = "APIs for role management")
public class RoleController {
	
	
	private final Logger logger;
	private final RoleServiceImpl roleService;
	
	public RoleController(RoleServiceImpl roleService) {
		super();
		this.logger = LoggerFactory.getLogger(RoleController.class);
		this.roleService = roleService;
	}

	// Create multiple roles
    @PostMapping("/bulk")
    public ResponseEntity<List<RoleDTO>> saveAllRoles(@RequestBody List<Role> roles) {
        try {
            logger.info("Request to save multiple roles: {}", roles);
            List<RoleDTO> savedRoles = roleService.saveAll(roles);
            return new ResponseEntity<>(savedRoles, HttpStatus.CREATED);
        } catch (EntityNotSaveException ex) {
            logger.error("Failed to save roles: {}", ex.getMessage(), ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get all roles
    @GetMapping
    public ResponseEntity<List<RoleDTO>> getAllRoles() {
        try {
            logger.info("Request to fetch all roles");
            List<RoleDTO> roles = roleService.findAll();
            return new ResponseEntity<>(roles, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Failed to fetch all roles: {}", ex.getMessage(), ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get roles by IDs
    @GetMapping("/ids")
    public ResponseEntity<List<RoleDTO>> getRolesByIds(@RequestParam List<Integer> ids) {
        try {
            logger.info("Request to fetch roles by IDs: {}", ids);
            List<RoleDTO> roles = roleService.findAllById(ids);
            return new ResponseEntity<>(roles, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Failed to fetch roles by IDs: {}", ex.getMessage(), ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Create a single role
    @PostMapping
    public ResponseEntity<Role> saveRole(@RequestBody Role role) {
        try {
            logger.info("Request to save role: {}", role);
            Role savedRole = roleService.save(role);
            return new ResponseEntity<>(savedRole, HttpStatus.CREATED);
        } catch (EntityNotSaveException ex) {
            logger.error("Failed to save role: {}", ex.getMessage(), ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get role by ID
    @GetMapping("/{id}")
    public ResponseEntity<RoleDTO> getRoleById(@PathVariable Integer id) {
        try {
            logger.info("Request to fetch role by ID: {}", id);
            RoleDTO role = roleService.findById(id);
            return  new ResponseEntity<>(role, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Failed to fetch role by ID: {}", ex.getMessage(), ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Check if role exists by ID
    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> existsById(@PathVariable Integer id) {
        try {
            logger.info("Request to check if role exists by ID: {}", id);
            boolean exists = roleService.existsById(id);
            return new ResponseEntity<>(exists, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Failed to check if role exists by ID: {}", ex.getMessage(), ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get total count of roles
    @GetMapping("/count")
    public ResponseEntity<Long> getRoleCount() {
        try {
            logger.info("Request to count roles");
            long count = roleService.count();
            return new ResponseEntity<>(count, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Failed to count roles: {}", ex.getMessage(), ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete role by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoleById(@PathVariable Integer id) {
        try {
            logger.info("Request to delete role by ID: {}", id);
            roleService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ex) {
            logger.error("Failed to delete role by ID: {}", ex.getMessage(), ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete a role
    @DeleteMapping
    public ResponseEntity<Void> deleteRole(@RequestBody Role role) {
        try {
            logger.info("Request to delete role: {}", role);
            roleService.delete(role);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ex) {
            logger.error("Failed to delete role: {}", ex.getMessage(), ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete roles by IDs
    @DeleteMapping("/bulk")
    public ResponseEntity<Void> deleteRolesByIds(@RequestParam List<Integer> ids) {
        try {
            logger.info("Request to delete roles by IDs: {}", ids);
            roleService.deleteAllById(ids);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ex) {
            logger.error("Failed to delete roles by IDs: {}", ex.getMessage(), ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete all roles
    @DeleteMapping("/all")
    public ResponseEntity<Void> deleteAllRoles() {
        try {
            logger.info("Request to delete all roles");
            roleService.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ex) {
            logger.error("Failed to delete all roles: {}", ex.getMessage(), ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update role by ID
    @PutMapping("/{id}")
    public ResponseEntity<RoleDTO> updateRole(@PathVariable Integer id, @RequestBody Role newRole) {
        try {
            logger.info("Request to update role with ID: {}", id);
            RoleDTO updatedRole = roleService.updateRole(id, newRole);
            return new ResponseEntity<>(updatedRole, HttpStatus.OK);
        } catch (EntityNotFoundException ex) {
            logger.error("Role not found with ID: {}", id, ex);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            logger.error("Failed to update role with ID: {}", id, ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
