package com.Contact.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
public class UserProfileRequestDto {

	@NotBlank(message = "bloodType is required")
	private String bloodType;

	@Size(max = 1000, message = "allergies must be at most 1000 characters")
	private String allergies;

	@Size(max = 1000, message = "medicalConditions must be at most 1000 characters")
	private String medicalConditions;

	@Size(max = 100, message = "vehicleLicensePlate must be at most 100 characters")
	private String vehicleLicensePlate;
}

