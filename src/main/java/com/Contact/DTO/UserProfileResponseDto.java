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
public class UserProfileResponseDto {

	private Long id;
	private String userName;
	private String bloodType;
	private String allergies;
	private String medicalConditions;
	private String vehicleLicensePlate;
}

