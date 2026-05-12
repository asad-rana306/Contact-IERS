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
		// Using null for allowedOrigins combined with allowCredentials false
		// is equivalent to allowing all origins
		configuration.setAllowedOriginPatterns(Arrays.asList("*"));

		// Allow all HTTP methods
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH", "HEAD"));

		// Allow all headers
		configuration.setAllowedHeaders(Arrays.asList("*"));

		// Expose necessary headers to clients
		configuration.setExposedHeaders(Arrays.asList(
				"Authorization",
				"Content-Type",
				"X-Total-Count",
				"X-Page-Number",
				"X-Page-Size",
				"Access-Control-Allow-Origin",
				"Access-Control-Allow-Credentials"
		));

		// Allow credentials (important for production with gateways)
		configuration.setAllowCredentials(true);

		// Cache preflight requests for 1 hour (3600 seconds)
		configuration.setMaxAge(3600L);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		// Apply CORS to all endpoints
		source.registerCorsConfiguration("/**", configuration);

		return source;
	}
}

