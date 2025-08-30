package com.example.userservice.dto;

import java.util.List;

public class UserRequest {
	  private String name;
	  private String email;
	  private String password;
	  private List<PhoneDto> phones;
	  

	  /** PhoneDto son los datos que vienen del JSON del cliente. 
	   * Para no exponer directamente las entidades y tener más control sobre la API.*/
	  
	  public static class PhoneDto {
	        private String number;
	        private String citycode;
	        private String contrycode;
	        
	        // metodos getters y setters
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
	  
	// metosos getters y setters
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
	public List<PhoneDto> getPhones() {
		return phones;
	}
	public void setPhones(List<PhoneDto> phones) {
		this.phones = phones;
	}



}
