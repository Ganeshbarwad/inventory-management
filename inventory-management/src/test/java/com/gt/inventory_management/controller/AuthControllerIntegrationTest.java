package com.gt.inventory_management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gt.inventory_management.dto.LoginRequestDTO;
import com.gt.inventory_management.dto.RegisterRequestDTO;
import com.gt.inventory_management.model.Role;
import com.gt.inventory_management.repo.RoleRepository;
import com.gt.inventory_management.repo.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        // ensure role exists for registration/login
        roleRepository.findByName("USER").orElseGet(() ->
                roleRepository.save(new Role(null, "USER"))
        );
    }

    @Test
    void testRegisterUser() throws Exception {
        RegisterRequestDTO register = new RegisterRequestDTO();
        register.setName("Ganesh");
        register.setEmail("Ganesh@example.com");
        register.setRole("ADMIN");
        register.setPassword("password123");
        register.setMobileNo("9876543210");
        register.setAddress("Pune, India");

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(register)))
                .andExpect(status().isOk()); // adjust if your controller returns CREATED (201)
    }

    @Test
    void testLoginUser() throws Exception {
//        // manually add a user before login
//        Role role = roleRepository.findByName("USER").get();
//

        LoginRequestDTO request = new LoginRequestDTO();
        request.setEmail("Ganesh@example.com");
        request.setPassword("password123");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }
}
