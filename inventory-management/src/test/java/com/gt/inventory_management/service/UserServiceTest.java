package com.gt.inventory_management.service;

import com.gt.inventory_management.dto.LoginRequestDTO;
import com.gt.inventory_management.dto.LoginResponseDTO;
import com.gt.inventory_management.dto.RegisterRequestDTO;
import com.gt.inventory_management.exception.AuthenticationFailureException;
import com.gt.inventory_management.exception.InvalidInputException;
import com.gt.inventory_management.model.Role;
import com.gt.inventory_management.model.User;
import com.gt.inventory_management.repo.RoleRepository;
import com.gt.inventory_management.repo.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepo;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private RoleRepository roleRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private RegisterRequestDTO registerRequest;
    private LoginRequestDTO loginRequest;
    private Role role;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        role = new Role(1L, "DEALER");
        user = User.builder()
                .id(1L)
                .name("Ganesh")
                .email("ganesh@example.com")
                .password("encodedPass")
                .role(role)
                .build();

        registerRequest = new RegisterRequestDTO("Ganesh", "ganesh@example.com", "123456", "DEALER", "9999999999", "Pune");
        loginRequest = new LoginRequestDTO("ganesh@example.com", "123456");
    }

    @Test
    void testRegisterUser_Success() {
        when(userRepo.existsByEmail(anyString())).thenReturn(false);
        when(roleRepo.findByName(anyString())).thenReturn(Optional.of(role));
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPass");
        when(userRepo.save(any(User.class))).thenReturn(user);

        String response = userService.registerUser(registerRequest);

        assertTrue(response.contains("User registered successfully"));
        verify(userRepo, times(1)).save(any(User.class));
    }

    @Test
    void testRegisterUser_EmailExists() {
        when(userRepo.existsByEmail(anyString())).thenReturn(true);

        String response = userService.registerUser(registerRequest);

        assertEquals("Email already registered!", response);
    }

    @Test
    void testRegisterUser_InvalidRole() {
        when(userRepo.existsByEmail(anyString())).thenReturn(false);
        when(roleRepo.findByName(anyString())).thenReturn(Optional.empty());

        assertThrows(InvalidInputException.class, () -> userService.registerUser(registerRequest));
    }

    @Test
    void testLogin_Success() {
        when(userRepo.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(anyString())).thenReturn("mockToken");

        LoginResponseDTO response = userService.login(loginRequest);

        assertNotNull(response);
        assertEquals("mockToken", response.getToken());
        assertEquals("Login successful!", response.getMessage());
    }

    @Test
    void testLogin_UserNotFound() {
        when(userRepo.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(AuthenticationFailureException.class, () -> userService.login(loginRequest));
    }
}
