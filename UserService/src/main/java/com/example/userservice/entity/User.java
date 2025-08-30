package com.example.userservice.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String password;

	@OneToMany(cascade = { CascadeType.ALL }, orphanRemoval = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private List<Phone> phones;

	private String token;
	private Boolean isActive;
	private LocalDateTime created;
	private LocalDateTime modified;
	private LocalDateTime lastLogin;

	// metodos getters y setters.
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Phone> getPhones() {
		return phones;
	}

	public void setPhones(List<Phone> phones) {
		this.phones = phones;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	public LocalDateTime getModified() {
		return modified;
	}

	public void setModified(LocalDateTime modified) {
		this.modified = modified;
	}

	public LocalDateTime getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(LocalDateTime lastLogin) {
		this.lastLogin = lastLogin;
	}

	@PrePersist
	public void prePersist() {
		LocalDateTime now = LocalDateTime.now();
		this.created = now;
		this.modified = now;
		this.lastLogin = now;
		this.isActive = true;
		if (this.token == null)
			this.token = UUID.randomUUID().toString();
	}

	@PreUpdate
	public void preUpdate() {
		this.modified = LocalDateTime.now();
	}
}
