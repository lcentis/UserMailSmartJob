package com.example.userservice.service;

import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.userservice.dto.*;
import com.example.userservice.entity.Phone;
import com.example.userservice.entity.User;
import com.example.userservice.repository.UserRepository;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class UserService {
	private final UserRepository userRepository;
	private final Pattern emailPattern;
	private final Pattern passwordPattern;

	public UserService(UserRepository repo, @Qualifier("emailPattern") Pattern emailPattern,
			@Qualifier("passwordPattern") Pattern passwordPattern) {
		this.userRepository = repo;
		this.emailPattern = emailPattern;
		this.passwordPattern = passwordPattern;
	}

	@Transactional
	public UserResponse register(UserRequest req) {
		if (req == null) {
			throw new IllegalArgumentException("Solicitud vacía");
		}

		// Validaciones básicas
		if (req.getEmail() == null || !emailPattern.matcher(req.getEmail()).matches()) {
			throw new IllegalArgumentException("Correo con formato inválido");
		}
		if (req.getPassword() == null || !passwordPattern.matcher(req.getPassword()).matches()) {
			throw new IllegalArgumentException("Contraseña con formato inválido");
		}

		Optional<User> exists = userRepository.findByEmail(req.getEmail());
		if (exists.isPresent()) {
			throw new IllegalStateException("El correo ya registrado");
		}

		User u = new User();
		u.setName(req.getName());
		u.setEmail(req.getEmail());
		u.setPassword(req.getPassword());
		if (req.getPhones() != null) {
			u.setPhones(req.getPhones().stream().map(p -> {
				Phone phone = new Phone();
				phone.setNumber(p.getNumber());
				phone.setCitycode(p.getCitycode());
				phone.setContrycode(p.getContrycode());
				return phone;
			}).collect(Collectors.toList()));
		}

		User saved = userRepository.save(u);

		UserResponse resp = mapToResponse(saved);
		return resp;
	}

	private UserResponse mapToResponse(User saved) {
		UserResponse r = new UserResponse();
		r.setId(saved.getId());
		r.setCreated(saved.getCreated());
		r.setModified(saved.getModified());
		r.setLast_login(saved.getLastLogin());
		r.setToken(saved.getToken());
		r.setIsactive(saved.getIsActive());
		r.setName(saved.getName());
		r.setEmail(saved.getEmail());
		if (saved.getPhones() != null) {
			r.setPhones(saved.getPhones().stream().map(p -> {
				UserResponse.PhoneDto pd = new UserResponse.PhoneDto();
				pd.setNumber(p.getNumber());
				pd.setCitycode(p.getCitycode());
				pd.setContrycode(p.getContrycode());
				return pd;
			}).collect(Collectors.toList()));
		}
		return r;
	}
	
	public List<UserResponse> findAll() {
	    return userRepository.findAll().stream()
	            .map(this::mapToResponse)
	            .collect(Collectors.toList());
	}
}
