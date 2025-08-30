package com.example.userservice.controller;

import com.example.userservice.dto.*;
import com.example.userservice.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/users", produces = "application/json")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	 @PostMapping(value = "/register", consumes = "application/json")
	public ResponseEntity<?> register(@RequestBody UserRequest request) {
		try {
			UserResponse resp = userService.register(request);
			return ResponseEntity.status(HttpStatus.CREATED).body(resp);
		} catch (IllegalStateException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(error(e.getMessage()));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error(e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error("Error interno"));
		}
	}

	@GetMapping
	public ResponseEntity<?> listUsers() {
	    return ResponseEntity.ok(userService.findAll());
	}
	
	private java.util.Map<String, String> error(String msg) {
		return java.util.Collections.singletonMap("mensaje", msg);
	}

}
