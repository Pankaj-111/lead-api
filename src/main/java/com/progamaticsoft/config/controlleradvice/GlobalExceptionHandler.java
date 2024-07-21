package com.progamaticsoft.config.controlleradvice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handleResourceNotFoundException(final ResourceNotFoundException ex,
			final WebRequest request) {
		final ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}
}
