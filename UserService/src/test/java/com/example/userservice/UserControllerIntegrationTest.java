package com.example.userservice;

import com.example.userservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test") // usa application-test.properties
public class UserControllerIntegrationTest {
	@Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void cleanDb() {
        userRepository.deleteAll();
    }

    private String validPayload(String email) {
        return "{\n" +
                "  \"name\": \"Juan Rodriguez\",\n" +
                "  \"email\": \"" + email + "\",\n" +
                "  \"password\": \"Hunter123\",\n" +
                "  \"phones\": [ { \"number\": \"1234567\", \"citycode\": \"1\", \"contrycode\": \"57\" } ]\n" +
                "}";
    }

    @Test
    void register_success_returns201_andToken() throws Exception {
        mockMvc.perform(
                post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validPayload("juan@rodriguez.org"))
        )
        .andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id", notNullValue()))
        .andExpect(jsonPath("$.token", notNullValue()))
        .andExpect(jsonPath("$.email", is("juan@rodriguez.org")))
        .andExpect(jsonPath("$.isactive", is(true)))
        .andExpect(jsonPath("$.created", notNullValue()))
        .andExpect(jsonPath("$.last_login", notNullValue()));
    }

    @Test
    void register_duplicateEmail_returns409() throws Exception {
        // Primero, alta exitosa
        mockMvc.perform(
                post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validPayload("dup@correo.com"))
        ).andExpect(status().isCreated());

        // Segundo intento con el mismo email -> 409
        mockMvc.perform(
                post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validPayload("dup@correo.com"))
        )
        .andExpect(status().isConflict())
        .andExpect(jsonPath("$.mensaje", is("El correo ya registrado")));
    }

    @Test
    void register_invalidEmail_returns400() throws Exception {
        String bad = "{\n" +
                "  \"name\": \"Juan\",\n" +
                "  \"email\": \"correo-invalido\",\n" +
                "  \"password\": \"Hunter123\",\n" +
                "  \"phones\": []\n" +
                "}";

        mockMvc.perform(
                post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bad)
        )
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.mensaje", is("Correo con formato inválido")));
    }

    @Test
    void listUsers_returns200_andArray() throws Exception {
        // Crea un usuario para que el GET no devuelva []
        mockMvc.perform(
                post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validPayload("list@ok.com"))
        ).andExpect(status().isCreated());

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", not(empty())))
                .andExpect(jsonPath("$[0].email", is("list@ok.com")));
    }
}
