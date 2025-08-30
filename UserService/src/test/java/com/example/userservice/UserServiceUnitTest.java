package com.example.userservice;

import com.example.userservice.dto.UserRequest;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.service.UserService;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceUnitTest {
	@Test
	void duplicateEmail_throwsIllegalState() {
		UserRepository repo = mock(UserRepository.class);
		when(repo.findByEmail("dup@x.com")).thenReturn(Optional.of(new com.example.userservice.entity.User()));

		Pattern emailP = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");
		Pattern passP = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$");

		UserService svc = new UserService(repo, emailP, passP);

		UserRequest req = new UserRequest();
		req.setName("Test");
		req.setEmail("dup@x.com");
		req.setPassword("Abcdef12");

		assertThrows(IllegalStateException.class, () -> svc.register(req));
		verify(repo, times(1)).findByEmail("dup@x.com");
	}
}
