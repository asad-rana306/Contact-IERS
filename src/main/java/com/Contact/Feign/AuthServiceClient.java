package com.Contact.Feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "IERS-Authentication-Service")
public interface AuthServiceClient {

	@GetMapping("/api/auth/validate")
	String validate(@RequestHeader("Authorization") String authHeader);
}

