package com.Contact.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactResponseDto {

	private Long id;
	private String userName;
	private String name;
	private String phoneNumber;
	private String relation;
	private Integer priority;
	private Boolean isVerified;
}

