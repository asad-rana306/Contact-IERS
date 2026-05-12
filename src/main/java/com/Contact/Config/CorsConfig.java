package com.Contact.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class CorsConfig {

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();

		// Allow requests from any origin (Flutter apps, browsers, etc.)
		configuration.setAllowedOrigins(Arrays.asList("*"));

		// Allow all HTTP methods
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));

		// Allow all headers
		configuration.setAllowedHeaders(Arrays.asList("*"));

		// Expose necessary headers to clients
		configuration.setExposedHeaders(Arrays.asList(
				"Authorization",
				"Content-Type",
				"X-Total-Count",
				"X-Page-Number",
				"X-Page-Size"
		));

		// Allow credentials
		configuration.setAllowCredentials(false);

		// Cache preflight requests for 1 hour
		configuration.setMaxAge(3600L);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		// Apply CORS to all endpoints
		source.registerCorsConfiguration("/**", configuration);

		return source;
	}
}

