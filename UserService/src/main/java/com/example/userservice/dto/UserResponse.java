package com.example.userservice.dto;

import java.time.LocalDateTime;
import java.util.List;

public class UserResponse {

	private Long id;
	private LocalDateTime created;
	private LocalDateTime modified;
	private LocalDateTime last_login;
	private String token;
	private Boolean isactive;
	private String name;
	private String email;
	private List<PhoneDto> phones;

	 /** PhoneDto son los datos que van en JSON al cliente. 
	   * Para no exponer directamente las entidades y tener más control sobre la API.*/
	public static class PhoneDto {
		private String number;
		private String citycode;
		private String contrycode;

		public String getNumber() {
			return number;
		}

		public void setNumber(String number) {
			this.number = number;
		}

		public String getCitycode() {
			return citycode;
		}

		public void setCitycode(String citycode) {
			this.citycode = citycode;
		}

		public String getContrycode() {
			return contrycode;
		}

		public void setContrycode(String contrycode) {
			this.contrycode = contrycode;
		}
	}

	// metodos getters y setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public LocalDateTime getLast_login() {
		return last_login;
	}

	public void setLast_login(LocalDateTime last_login) {
		this.last_login = last_login;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Boolean getIsactive() {
		return isactive;
	}

	public void setIsactive(Boolean isactive) {
		this.isactive = isactive;
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

	public List<PhoneDto> getPhones() {
		return phones;
	}

	public void setPhones(List<PhoneDto> phones) {
		this.phones = phones;
	}

}
