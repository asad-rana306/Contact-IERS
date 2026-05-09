package com.Contact.Service;

import com.Contact.DTO.ContactRequestDto;
import com.Contact.DTO.ContactResponseDto;
import com.Contact.Entity.Contact;
import com.Contact.Exception.UnauthorizedException;
import com.Contact.Feign.AuthServiceClient;
import com.Contact.Repository.ContactRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ContactService {

	private static final Logger log = LoggerFactory.getLogger(ContactService.class);

	private final AuthServiceClient authServiceClient;
	private final ContactRepository contactRepository;

	@Transactional
	public ContactResponseDto addContact(String authHeader, ContactRequestDto requestDto) {
		String userName = resolveUserName(authHeader);
		log.info("Creating emergency contact for userName={} with priority={}", userName, requestDto.getPriority());

		Contact contact = Contact.builder()
				.userName(userName)
				.name(requestDto.getName())
				.phoneNumber(requestDto.getPhoneNumber())
				.relation(requestDto.getRelation())
				.priority(requestDto.getPriority())
				.isVerified(false)
				.build();

		Contact savedContact = contactRepository.save(contact);
		log.info("Emergency contact created with id={} for userName={}", savedContact.getId(), userName);
		return toResponse(savedContact);
	}

	@Transactional(readOnly = true)
	public List<ContactResponseDto> getUserContacts(String authHeader) {
		String userName = resolveUserName(authHeader);
		log.info("Fetching emergency contacts for userName={}", userName);

		return contactRepository.findByUserNameOrderByPriorityAsc(userName)
				.stream()
				.map(this::toResponse)
				.toList();
	}

	private String resolveUserName(String authHeader) {
		String userName = authServiceClient.validate(authHeader);
		if (userName == null || userName.isBlank()) {
			throw new UnauthorizedException("Unable to resolve user from authorization token");
		}
		return userName.trim();
	}

	private ContactResponseDto toResponse(Contact contact) {
		return ContactResponseDto.builder()
				.id(contact.getId())
				.userName(contact.getUserName())
				.name(contact.getName())
				.phoneNumber(contact.getPhoneNumber())
				.relation(contact.getRelation())
				.priority(contact.getPriority())
				.isVerified(contact.getIsVerified())
				.build();
	}
}

