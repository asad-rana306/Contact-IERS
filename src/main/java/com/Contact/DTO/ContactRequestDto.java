package com.Contact.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class ContactRequestDto {

	@NotBlank(message = "name is required")
	private String name;

	@NotBlank(message = "phoneNumber is required")
	private String phoneNumber;

	@NotBlank(message = "relation is required")
	private String relation;

	@NotNull(message = "priority is required")
	@Min(value = 1, message = "priority must be greater than or equal to 1")
	private Integer priority;
}

