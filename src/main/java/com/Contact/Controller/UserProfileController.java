package com.Contact.Controller;

import com.Contact.DTO.UserProfileRequestDto;
import com.Contact.DTO.UserProfileResponseDto;
import com.Contact.Service.UserProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
public class UserProfileController {

	private static final Logger log = LoggerFactory.getLogger(UserProfileController.class);

	private final UserProfileService userProfileService;

	@PostMapping
	public ResponseEntity<UserProfileResponseDto> addProfile(
			@RequestHeader("Authorization") String authHeader,
			@Valid @RequestBody UserProfileRequestDto requestDto) {
		log.info("Received add profile request");
		UserProfileResponseDto response = userProfileService.addProfile(authHeader, requestDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@GetMapping
	public ResponseEntity<UserProfileResponseDto> getProfile(
			@RequestHeader("Authorization") String authHeader) {
		log.info("Received get profile request");
		UserProfileResponseDto response = userProfileService.getProfile(authHeader);
		return ResponseEntity.ok(response);
	}
}

