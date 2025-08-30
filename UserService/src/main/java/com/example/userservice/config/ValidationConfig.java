package com.example.userservice.config;

import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ValidationConfig {

	@Bean(name = "emailPattern")
	public Pattern emailPattern(@Value("${validation.email.regex:^[^@]+@[^@]+[.][A-Za-z]{2,6}$}") String emailRegex) {
		return Pattern.compile(emailRegex);
	}

	@Bean(name = "passwordPattern")
	public Pattern passwordPattern(@Value("${validation.password.regex:^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$}") String passwordRegex) {
		return Pattern.compile(passwordRegex);
	}

}
