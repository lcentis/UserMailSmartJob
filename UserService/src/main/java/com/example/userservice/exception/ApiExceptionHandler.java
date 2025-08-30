package com.example.userservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class ApiExceptionHandler {

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<?> handleBadRequest(IllegalArgumentException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(java.util.Collections.singletonMap("mensaje", ex.getMessage()));
	}

	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<?> handleConflict(IllegalStateException ex) {
		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(java.util.Collections.singletonMap("mensaje", ex.getMessage()));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleAll(Exception ex) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(java.util.Collections.singletonMap("mensaje", "Error interno"));
	}

}
