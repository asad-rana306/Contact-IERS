package com.Contact.Service;

import com.Contact.DTO.UserProfileRequestDto;
import com.Contact.DTO.UserProfileResponseDto;
import com.Contact.Entity.UserProfile;
import com.Contact.Exception.ResourceConflictException;
import com.Contact.Exception.ResourceNotFoundException;
import com.Contact.Exception.UnauthorizedException;
import com.Contact.Feign.AuthServiceClient;
import com.Contact.Repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserProfileService {

	private static final Logger log = LoggerFactory.getLogger(UserProfileService.class);

	private final AuthServiceClient authServiceClient;
	private final UserProfileRepository userProfileRepository;

	@Transactional
	public UserProfileResponseDto addProfile(String authHeader, UserProfileRequestDto requestDto) {
		String userName = resolveUserName(authHeader);
		log.info("Creating profile for userName={}", userName);

		if (userProfileRepository.findByUserName(userName).isPresent()) {
			throw new ResourceConflictException("Profile already exists for user " + userName);
		}

		UserProfile profile = UserProfile.builder()
				.userName(userName)
				.bloodType(requestDto.getBloodType())
				.allergies(requestDto.getAllergies())
				.medicalConditions(requestDto.getMedicalConditions())
				.vehicleLicensePlate(requestDto.getVehicleLicensePlate())
				.build();

		UserProfile savedProfile = userProfileRepository.save(profile);
		log.info("Profile created with id={} for userName={}", savedProfile.getId(), userName);
		return toResponse(savedProfile);
	}

	@Transactional(readOnly = true)
	public UserProfileResponseDto getProfile(String authHeader) {
		String userName = resolveUserName(authHeader);
		log.info("Fetching profile for userName={}", userName);

		UserProfile profile = userProfileRepository.findByUserName(userName)
				.orElseThrow(() -> new ResourceNotFoundException("Profile not found for user " + userName));

		return toResponse(profile);
	}

	private String resolveUserName(String authHeader) {
		String userName = authServiceClient.validate(authHeader);
		if (userName == null || userName.isBlank()) {
			throw new UnauthorizedException("Unable to resolve user from authorization token");
		}
		return userName.trim();
	}

	private UserProfileResponseDto toResponse(UserProfile profile) {
		return UserProfileResponseDto.builder()
				.id(profile.getId())
				.userName(profile.getUserName())
				.bloodType(profile.getBloodType())
				.allergies(profile.getAllergies())
				.medicalConditions(profile.getMedicalConditions())
				.vehicleLicensePlate(profile.getVehicleLicensePlate())
				.build();
	}
}

