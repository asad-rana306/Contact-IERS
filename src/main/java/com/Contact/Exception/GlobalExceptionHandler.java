package com.Contact.Exception;

import com.Contact.DTO.ApiErrorResponseDto;
import feign.FeignException;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex,
			HttpHeaders headers,
			HttpStatusCode status,
			WebRequest request) {
		String message = ex.getBindingResult()
				.getFieldErrors()
				.stream()
				.map(this::formatFieldError)
				.collect(Collectors.joining(", "));

		return ResponseEntity.badRequest().body(buildError(HttpStatus.BAD_REQUEST, message, request.getDescription(false)));
	}

	@ExceptionHandler(MissingRequestHeaderException.class)
	public ResponseEntity<ApiErrorResponseDto> handleMissingHeader(
			MissingRequestHeaderException ex,
			HttpServletRequest request) {
		log.warn("Missing required header: {}", ex.getHeaderName());
		return ResponseEntity.badRequest().body(buildError(HttpStatus.BAD_REQUEST, ex.getMessage(), request.getRequestURI()));
	}

	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<ApiErrorResponseDto> handleUnauthorized(UnauthorizedException ex, HttpServletRequest request) {
		log.warn("Unauthorized request: {}", ex.getMessage());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body(buildError(HttpStatus.UNAUTHORIZED, ex.getMessage(), request.getRequestURI()));
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiErrorResponseDto> handleNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(buildError(HttpStatus.NOT_FOUND, ex.getMessage(), request.getRequestURI()));
	}

	@ExceptionHandler(ResourceConflictException.class)
	public ResponseEntity<ApiErrorResponseDto> handleConflict(ResourceConflictException ex, HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(buildError(HttpStatus.CONFLICT, ex.getMessage(), request.getRequestURI()));
	}

	@ExceptionHandler(FeignException.class)
	public ResponseEntity<ApiErrorResponseDto> handleFeign(FeignException ex, HttpServletRequest request) {
		log.error("Auth service call failed with status {}", ex.status(), ex);
		HttpStatus status = ex.status() == HttpStatus.UNAUTHORIZED.value() || ex.status() == HttpStatus.FORBIDDEN.value()
				? HttpStatus.UNAUTHORIZED
				: HttpStatus.BAD_GATEWAY;
		String message = status == HttpStatus.UNAUTHORIZED
				? "Authorization token is invalid or expired"
				: "Authentication service is unavailable";
		return ResponseEntity.status(status).body(buildError(status, message, request.getRequestURI()));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiErrorResponseDto> handleGeneral(Exception ex, HttpServletRequest request) {
		log.error("Unhandled exception", ex);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(buildError(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected server error", request.getRequestURI()));
	}

	private String formatFieldError(FieldError error) {
		return error.getField() + " " + error.getDefaultMessage();
	}

	private ApiErrorResponseDto buildError(HttpStatus status, String message, String path) {
		return ApiErrorResponseDto.builder()
				.timestamp(Instant.now())
				.status(status.value())
				.error(status.getReasonPhrase())
				.message(message)
				.path(path)
				.build();
	}
}

