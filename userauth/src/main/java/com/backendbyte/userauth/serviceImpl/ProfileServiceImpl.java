package com.backendbyte.userauth.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.backendbyte.userauth.dto.ProfileDTO;
import com.backendbyte.userauth.entity.Profile;
import com.backendbyte.userauth.exception.EntityNotFoundException;
import com.backendbyte.userauth.exception.EntityNotSaveException;
import com.backendbyte.userauth.mapper.ProfileMapper;
import com.backendbyte.userauth.repository.ProfileRepository;
import com.backendbyte.userauth.service.ProfileService;

@Service
public class ProfileServiceImpl implements ProfileService{
	
	private final Logger logger;
	private ProfileRepository profileRepo;
	
	public ProfileServiceImpl(ProfileRepository profileRepo) {
		this.logger = LoggerFactory.getLogger(ProfileServiceImpl.class);
		this.profileRepo = profileRepo;
	}

	@Override
	public List<ProfileDTO> saveAll(Iterable<Profile> profiles) {
		try {
            logger.info("Saving multiple profiles: {}", profiles);
            return profileRepo.saveAll(profiles).stream()
            		.map(ProfileMapper::toDTO)
            		.collect(Collectors.toList());
        } catch (Exception ex) {
            logger.error("Failed to save profiles: {}", ex.getMessage(), ex);
            throw new EntityNotSaveException("Failed to save profiles", ex);
        }
	}

	@Override
	public List<ProfileDTO> findAll() {
		try {
            logger.info("Fetching all profiles");
            return profileRepo.findAll().stream()
            		.map(ProfileMapper::toDTO)
            		.collect(Collectors.toList());
        } catch (Exception ex) {
            logger.error("Failed to fetch all profiles: {}", ex.getMessage(), ex);
            throw new RuntimeException("Failed to fetch all profiles", ex);
        }
	}

	@Override
	public List<ProfileDTO> findAllById(Iterable<Long> ids) {
		try {
            logger.info("Fetching profiles by IDs: {}", ids);
            return profileRepo.findAllById(ids).stream()
            		.map(ProfileMapper::toDTO)
            		.collect(Collectors.toList());
        } catch (Exception ex) {
            logger.error("Failed to fetch profiles by IDs: {}", ex.getMessage(), ex);
            throw new RuntimeException("Failed to fetch profiles by IDs", ex);
        }
	}

	@Override
	public <S extends Profile> S save(S entity) {
		try {
            logger.info("Saving profile: {}", entity);
            return profileRepo.save(entity);
        } catch (Exception ex) {
            logger.error("Failed to save profile: {}", ex.getMessage(), ex);
            throw new EntityNotSaveException("Failed to save profile", ex);
        }
	}

	@Override
	public ProfileDTO findById(Long id) {
		try {
            logger.info("Fetching profile by ID: {}", id);
            Profile profile = profileRepo.findById(id)
            		.orElseThrow(() -> new EntityNotFoundException("Profile not found with ID: "+id));
            return ProfileMapper.toDTO(profile);
        } catch (Exception ex) {
            logger.error("Failed to fetch profile by ID: {}", ex.getMessage(), ex);
            throw new RuntimeException("Failed to fetch profile by ID", ex);
        }
	}

	@Override
	public boolean existsById(Long id) {
		try {
            logger.info("Checking if profile exists by ID: {}", id);
            return profileRepo.existsById(id);
        } catch (Exception ex) {
            logger.error("Failed to check if profile exists by ID: {}", ex.getMessage(), ex);
            throw new RuntimeException("Failed to check if profile exists by ID", ex);
        }
	}

	@Override
    public long count() {
        try {
            logger.info("Counting profiles");
            return profileRepo.count();
        } catch (Exception ex) {
            logger.error("Failed to count profiles: {}", ex.getMessage(), ex);
            throw new RuntimeException("Failed to count profiles", ex);
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            logger.info("Deleting profile by ID: {}", id);
            profileRepo.deleteById(id);
        } catch (Exception ex) {
            logger.error("Failed to delete profile by ID: {}", ex.getMessage(), ex);
            throw new RuntimeException("Failed to delete profile by ID", ex);
        }
    }

    @Override
    public void delete(Profile profile) {
        try {
            logger.info("Deleting profile: {}", profile);
            profileRepo.delete(profile);
        } catch (Exception ex) {
            logger.error("Failed to delete profile: {}", ex.getMessage(), ex);
            throw new RuntimeException("Failed to delete profile", ex);
        }
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> ids) {
        try {
            logger.info("Deleting profiles by IDs: {}", ids);
            profileRepo.deleteAllById(ids);
        } catch (Exception ex) {
            logger.error("Failed to delete profiles by IDs: {}", ex.getMessage(), ex);
            throw new RuntimeException("Failed to delete profiles by IDs", ex);
        }
    }

    @Override
    public void deleteAll(Iterable<? extends Profile> entities) {
        try {
            logger.info("Deleting multiple profiles: {}", entities);
            profileRepo.deleteAll(entities);
        } catch (Exception ex) {
            logger.error("Failed to delete multiple profiles: {}", ex.getMessage(), ex);
            throw new RuntimeException("Failed to delete multiple profiles", ex);
        }
    }

    @Override
    public void deleteAll() {
        try {
            logger.info("Deleting all profiles");
            profileRepo.deleteAll();
        } catch (Exception ex) {
            logger.error("Failed to delete all profiles: {}", ex.getMessage(), ex);
            throw new RuntimeException("Failed to delete all profiles", ex);
        }
    }

    @Override
    public ProfileDTO updateProfile(Long id, Profile newProfile) {
        try {
            logger.info("Updating profile with ID: {}", id);
            Profile profile = profileRepo.findById(id)
                    .map(existingProfile -> {
                        existingProfile.setFirstName(newProfile.getFirstName());
                        existingProfile.setMiddleName(newProfile.getMiddleName());
                        existingProfile.setLastLogin(newProfile.getLastLogin());
                        existingProfile.setEmail(newProfile.getEmail());
                        existingProfile.setMobileNo(newProfile.getMobileNo());
                        existingProfile.setLastLogin(newProfile.getLastLogin());
                        existingProfile.setUser(newProfile.getUser());
                        
                        return profileRepo.save(existingProfile);
                    })
                    .orElseThrow(() -> new EntityNotFoundException("Profile not found with ID: " + id));
            return ProfileMapper.toDTO(profile);
        } catch (EntityNotFoundException ex) {
            logger.error("Profile not found with ID: {}", id, ex);
            throw new EntityNotFoundException("Profile not found with ID: "+ id);
        } catch (Exception ex) {
            logger.error("Failed to update profile with ID: {}", id, ex);
            throw new RuntimeException("Failed to update profile", ex);
        }
    }

}
