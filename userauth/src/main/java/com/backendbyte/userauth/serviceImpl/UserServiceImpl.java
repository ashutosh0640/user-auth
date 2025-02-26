package com.backendbyte.userauth.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.backendbyte.userauth.dto.UserDTO;
import com.backendbyte.userauth.dto.UserProfileDTO;
import com.backendbyte.userauth.entity.User;
import com.backendbyte.userauth.exception.EntityNotFoundException;
import com.backendbyte.userauth.exception.EntityNotSaveException;
import com.backendbyte.userauth.mapper.UserMapper;
import com.backendbyte.userauth.repository.UserRepository;
import com.backendbyte.userauth.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	private final Logger logger;
	private UserRepository userRepo;
	private PasswordEncoder passwordEncoder;
	
	public UserServiceImpl(UserRepository userRepo, PasswordEncoder passwordEncoder) {
		this.logger = LoggerFactory.getLogger(UserServiceImpl.class);
		this.userRepo = userRepo;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public List<UserDTO> saveAll(Iterable<User> users) {
		try {
            logger.info("Saving multiple roles: {}", users);
            users.forEach(user -> user.setPassword(passwordEncoder.encode(user.getPassword())));
            return  userRepo.saveAll(users).stream()
                    .map(UserMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            logger.error("Failed to save roles: {}", ex.getMessage(), ex);
            throw new EntityNotSaveException("Failed to save roles", ex);
        }
	}

	@Override
	public List<UserDTO> findAll() {
		try {
            logger.info("Fetching all roles");
            return userRepo.findAll().stream()
            		.map(UserMapper::toDTO)
            		.collect(Collectors.toList());
        } catch (Exception ex) {
            logger.error("Failed to fetch all roles: {}", ex.getMessage(), ex);
            throw new RuntimeException("Failed to fetch all roles", ex);
        }
	}

	@Override
	public List<UserDTO> findAllById(Iterable<Long> ids) {
		try {
            logger.info("Fetching roles by IDs: {}", ids);
            return userRepo.findAllById(ids).stream()
            		.map(UserMapper::toDTO)
            		.collect(Collectors.toList());
        } catch (Exception ex) {
            logger.error("Failed to fetch roles by IDs: {}", ex.getMessage(), ex);
            throw new RuntimeException("Failed to fetch roles by IDs", ex);
        }
	}

	@Override
	public UserDTO save(User entity) {
		try {
            logger.info("Saving role: {}", entity);
            entity.setPassword(passwordEncoder.encode(entity.getPassword()));
            return UserMapper.toDTO(userRepo.save(entity));
        } catch (Exception ex) {
            logger.error("Failed to save role: {}", ex.getMessage(), ex);
            throw new EntityNotSaveException("Failed to save role", ex);
        }
	}

	@Override
	public UserDTO findById(Long id) {
		try {
            logger.info("Fetching role by ID: {}", id);
            User user = userRepo.findById(id)
            		.orElseThrow(() -> new EntityNotFoundException("User not found with ID: "+id));
            return UserMapper.toDTO(user);
        } catch (Exception ex) {
            logger.error("Failed to fetch role by ID: {}", ex.getMessage(), ex);
            throw new RuntimeException("Failed to fetch role by ID", ex);
        }
	}

	@Override
	public boolean existsById(Long id) {
		try {
            logger.info("Checking if role exists by ID: {}", id);
            return userRepo.existsById(id);
        } catch (Exception ex) {
            logger.error("Failed to check if role exists by ID: {}", ex.getMessage(), ex);
            throw new RuntimeException("Failed to check if role exists by ID", ex);
        }
	}

	@Override
    public long count() {
        try {
            logger.info("Counting roles");
            return userRepo.count();
        } catch (Exception ex) {
            logger.error("Failed to count roles: {}", ex.getMessage(), ex);
            throw new RuntimeException("Failed to count roles", ex);
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            logger.info("Deleting role by ID: {}", id);
            userRepo.deleteById(id);
        } catch (Exception ex) {
            logger.error("Failed to delete role by ID: {}", ex.getMessage(), ex);
            throw new RuntimeException("Failed to delete role by ID", ex);
        }
    }

    @Override
    public void delete(User role) {
        try {
            logger.info("Deleting role: {}", role);
            userRepo.delete(role);
        } catch (Exception ex) {
            logger.error("Failed to delete role: {}", ex.getMessage(), ex);
            throw new RuntimeException("Failed to delete role", ex);
        }
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> ids) {
        try {
            logger.info("Deleting roles by IDs: {}", ids);
            userRepo.deleteAllById(ids);
        } catch (Exception ex) {
            logger.error("Failed to delete roles by IDs: {}", ex.getMessage(), ex);
            throw new RuntimeException("Failed to delete roles by IDs", ex);
        }
    }

    @Override
    public void deleteAll(Iterable<? extends User> entities) {
        try {
            logger.info("Deleting multiple roles: {}", entities);
            userRepo.deleteAll(entities);
        } catch (Exception ex) {
            logger.error("Failed to delete multiple roles: {}", ex.getMessage(), ex);
            throw new RuntimeException("Failed to delete multiple roles", ex);
        }
    }

    @Override
    public void deleteAll() {
        try {
            logger.info("Deleting all roles");
            userRepo.deleteAll();
        } catch (Exception ex) {
            logger.error("Failed to delete all roles: {}", ex.getMessage(), ex);
            throw new RuntimeException("Failed to delete all roles", ex);
        }
    }

    @Override
    public UserDTO updateUser(Long id, User newUser) {
        try {
            logger.info("Updating role with ID: {}", id);
            User user = userRepo.findById(id)
                    .map(existingUser -> {
                    	existingUser.setUsername(newUser.getUsername());
                        existingUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
                        return userRepo.save(existingUser);
                    })
                    .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + id));
            return UserMapper.toDTO(user);
        } catch (EntityNotFoundException ex) {
            logger.error("User not found with ID: {}", id, ex);
            throw new EntityNotFoundException("User not found with ID: "+ id);
        } catch (Exception ex) {
            logger.error("Failed to update role with ID: {}", id, ex);
            throw new RuntimeException("Failed to update role", ex);
        }
    }

	@Override
	public UserDTO findByUsername(String username) {
		try {
			logger.info("Finding User by username: {}"+username);
			User user = userRepo.findByUsername(username)
					.orElseThrow(() -> new EntityNotFoundException("User not found with username: "+username));
			return UserMapper.toDTO(user);
		} catch (Exception ex) {
			logger.error("Failed to find user with username: {}", username , ex);
            throw new RuntimeException("Failed to find user with username"+ username, ex);
		}
	}

	@Override
	public UserProfileDTO findByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserProfileDTO findByMobileNo(String mobileNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserProfileDTO> findUserDetailsByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}
}
