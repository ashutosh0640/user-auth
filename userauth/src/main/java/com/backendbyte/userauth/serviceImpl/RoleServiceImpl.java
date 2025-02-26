package com.backendbyte.userauth.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.backendbyte.userauth.dto.RoleDTO;
import com.backendbyte.userauth.entity.Role;
import com.backendbyte.userauth.exception.EntityNotFoundException;
import com.backendbyte.userauth.exception.EntityNotSaveException;
import com.backendbyte.userauth.mapper.RoleMapper;
import com.backendbyte.userauth.repository.RoleRepository;
import com.backendbyte.userauth.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService{
	
	private final Logger logger;
	private RoleRepository roleRepo;
	
	public RoleServiceImpl(RoleRepository roleRepo) {
		this.logger = LoggerFactory.getLogger(RoleServiceImpl.class);
		this.roleRepo = roleRepo;
	}

	@Override
	public List<RoleDTO> saveAll(Iterable<Role> roles) {
		try {
            logger.info("Saving multiple roles: {}", roles);
            return roleRepo.saveAll(roles).stream()
            		.map(RoleMapper::toDTO)
            		.collect(Collectors.toList());
        } catch (Exception ex) {
            logger.error("Failed to save roles: {}", ex.getMessage(), ex);
            throw new EntityNotSaveException("Failed to save roles", ex);
        }
	}

	@Override
	public List<RoleDTO> findAll() {
		try {
            logger.info("Fetching all roles");
            return roleRepo.findAll().stream()
            		.map(RoleMapper::toDTO)
            		.collect(Collectors.toList());
        } catch (Exception ex) {
            logger.error("Failed to fetch all roles: {}", ex.getMessage(), ex);
            throw new RuntimeException("Failed to fetch all roles", ex);
        }
	}

	@Override
	public List<RoleDTO> findAllById(Iterable<Integer> ids) {
		try {
            logger.info("Fetching roles by IDs: {}", ids);
            return roleRepo.findAllById(ids).stream()
            		.map(RoleMapper::toDTO)
            		.collect(Collectors.toList());
        } catch (Exception ex) {
            logger.error("Failed to fetch roles by IDs: {}", ex.getMessage(), ex);
            throw new RuntimeException("Failed to fetch roles by IDs", ex);
        }
	}

	@Override
	public <S extends Role> S save(S entity) {
		try {
            logger.info("Saving role: {}", entity);
            return roleRepo.save(entity);
        } catch (Exception ex) {
            logger.error("Failed to save role: {}", ex.getMessage(), ex);
            throw new EntityNotSaveException("Failed to save role", ex);
        }
	}

	@Override
	public RoleDTO findById(Integer id) {
		try {
            logger.info("Fetching role by ID: {}", id);
            Role role = roleRepo.findById(id)
            		.orElseThrow(() -> new EntityNotFoundException("Role not found with ID: "+id));
            return RoleMapper.toDTO(role);
        } catch (Exception ex) {
            logger.error("Failed to fetch role by ID: {}", ex.getMessage(), ex);
            throw new RuntimeException("Failed to fetch role by ID", ex);
        }
	}

	@Override
	public boolean existsById(Integer id) {
		try {
            logger.info("Checking if role exists by ID: {}", id);
            return roleRepo.existsById(id);
        } catch (Exception ex) {
            logger.error("Failed to check if role exists by ID: {}", ex.getMessage(), ex);
            throw new RuntimeException("Failed to check if role exists by ID", ex);
        }
	}

	@Override
    public long count() {
        try {
            logger.info("Counting roles");
            return roleRepo.count();
        } catch (Exception ex) {
            logger.error("Failed to count roles: {}", ex.getMessage(), ex);
            throw new RuntimeException("Failed to count roles", ex);
        }
    }

    @Override
    public void deleteById(Integer id) {
        try {
            logger.info("Deleting role by ID: {}", id);
            roleRepo.deleteById(id);
        } catch (Exception ex) {
            logger.error("Failed to delete role by ID: {}", ex.getMessage(), ex);
            throw new RuntimeException("Failed to delete role by ID", ex);
        }
    }

    @Override
    public void delete(Role role) {
        try {
            logger.info("Deleting role: {}", role);
            roleRepo.delete(role);
        } catch (Exception ex) {
            logger.error("Failed to delete role: {}", ex.getMessage(), ex);
            throw new RuntimeException("Failed to delete role", ex);
        }
    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> ids) {
        try {
            logger.info("Deleting roles by IDs: {}", ids);
            roleRepo.deleteAllById(ids);
        } catch (Exception ex) {
            logger.error("Failed to delete roles by IDs: {}", ex.getMessage(), ex);
            throw new RuntimeException("Failed to delete roles by IDs", ex);
        }
    }

    @Override
    public void deleteAll(Iterable<? extends Role> entities) {
        try {
            logger.info("Deleting multiple roles: {}", entities);
            roleRepo.deleteAll(entities);
        } catch (Exception ex) {
            logger.error("Failed to delete multiple roles: {}", ex.getMessage(), ex);
            throw new RuntimeException("Failed to delete multiple roles", ex);
        }
    }

    @Override
    public void deleteAll() {
        try {
            logger.info("Deleting all roles");
            roleRepo.deleteAll();
        } catch (Exception ex) {
            logger.error("Failed to delete all roles: {}", ex.getMessage(), ex);
            throw new RuntimeException("Failed to delete all roles", ex);
        }
    }

    @Override
    public RoleDTO updateRole(int id, Role newRole) {
        try {
            logger.info("Updating role with ID: {}", id);
            Role role = roleRepo.findById(id)
                    .map(existingRole -> {
                        existingRole.setRoleName(newRole.getRoleName());
                        existingRole.setUsers(newRole.getUsers());
                        return roleRepo.save(existingRole);
                    })
                    .orElseThrow(() -> new EntityNotFoundException("Role not found with ID: " + id));
            return RoleMapper.toDTO(role);
        } catch (EntityNotFoundException ex) {
            logger.error("Role not found with ID: {}", id, ex);
            throw new EntityNotFoundException("Role not found with ID: "+ id);
        } catch (Exception ex) {
            logger.error("Failed to update role with ID: {}", id, ex);
            throw new RuntimeException("Failed to update role", ex);
        }
    }

}
