package com.Contact.Config;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Custom CORS filter that explicitly handles CORS headers for all requests.
 * This is particularly useful when the application is behind a reverse proxy or API Gateway.
 */
@Component
public class CorsFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// Get the request origin
		String origin = request.getHeader("Origin");

		// Set CORS headers for all requests
		// Allow any origin (if more restrictive, replace with specific domain)
		if (origin != null && !origin.isEmpty()) {
			response.setHeader("Access-Control-Allow-Origin", origin);
		} else {
			// Fallback if Origin header is missing
			response.setHeader("Access-Control-Allow-Origin", "*");
		}

		response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, PATCH, HEAD");
		response.setHeader("Access-Control-Allow-Headers", "Origin, Content-Type, Accept, Authorization, X-Requested-With, X-CSRF-Token");
		response.setHeader("Access-Control-Expose-Headers", "Authorization, Content-Type, X-Total-Count, X-Page-Number, X-Page-Size");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Credentials", "true");

		// Handle preflight requests
		if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
			response.setStatus(HttpServletResponse.SC_OK);
			return;
		}

		filterChain.doFilter(request, response);
	}
}

