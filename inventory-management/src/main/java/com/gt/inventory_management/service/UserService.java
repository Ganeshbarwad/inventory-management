package com.gt.inventory_management.service;

import com.gt.inventory_management.Security.JwtService;
import com.gt.inventory_management.dto.LoginRequestDTO;
import com.gt.inventory_management.dto.LoginResponseDTO;
import com.gt.inventory_management.dto.RegisterRequestDTO;
import com.gt.inventory_management.exception.AuthenticationFailureException;
import com.gt.inventory_management.exception.InvalidInputException;
import com.gt.inventory_management.model.Role;
import com.gt.inventory_management.model.User;
import com.gt.inventory_management.repo.RoleRepository;
import com.gt.inventory_management.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String registerUser(RegisterRequestDTO request) {
        if (userRepo.existsByEmail(request.getEmail())) {
            return "Email already registered!";
        }

        Role role = roleRepo.findByName(request.getRole().toUpperCase())
                .orElseThrow(() -> new InvalidInputException("Invalid Role: " + request.getRole()));

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .mobileNo(request.getMobileNo())
                .address(request.getAddress())
                .status("ACTIVE")
                .build();

        userRepo.save(user);
        return "User registered successfully with role: " + request.getRole();
    }

    public LoginResponseDTO login(LoginRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = userRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new AuthenticationFailureException("User Not Found"));

        String token = jwtService.generateToken(user.getEmail());
        return LoginResponseDTO.builder()
                .token(token)
                .message("Login successful!")
                .build();
    }
}
