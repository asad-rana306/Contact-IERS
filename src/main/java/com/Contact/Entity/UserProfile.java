package com.Contact.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
		name = "user_profiles",
		uniqueConstraints = @UniqueConstraint(name = "uk_user_profile_user_name", columnNames = "user_name")
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "user_name", nullable = false, unique = true)
	private String userName;

	@Column(name = "blood_type", nullable = false)
	private String bloodType;

	@Column(length = 1000)
	private String allergies;

	@Column(name = "medical_conditions", length = 1000)
	private String medicalConditions;

	@Column(name = "vehicle_license_plate", length = 100)
	private String vehicleLicensePlate;
}

