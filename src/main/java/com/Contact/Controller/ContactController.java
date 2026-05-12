package com.Contact.Controller;

import com.Contact.DTO.ContactRequestDto;
import com.Contact.DTO.ContactResponseDto;
import com.Contact.Service.ContactService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contacts")
@RequiredArgsConstructor
@CrossOrigin
public class ContactController {

	private static final Logger log = LoggerFactory.getLogger(ContactController.class);

	private final ContactService contactService;

	@PostMapping
	public ResponseEntity<ContactResponseDto> addContact(
			@RequestHeader("Authorization") String authHeader,
			@Valid @RequestBody ContactRequestDto requestDto) {
		log.info("Received add contact request");
		ContactResponseDto response = contactService.addContact(authHeader, requestDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@GetMapping
	public ResponseEntity<List<ContactResponseDto>> getUserContacts(
			@RequestHeader("Authorization") String authHeader) {
		log.info("Received get contacts request");
		List<ContactResponseDto> response = contactService.getUserContacts(authHeader);
		return ResponseEntity.ok(response);
	}
	@GetMapping("/health")
	public ResponseEntity<?> health() {
		return ResponseEntity.ok("User Profile Service is healthy");
	}
}

