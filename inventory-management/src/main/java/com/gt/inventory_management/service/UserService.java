package com.gt.inventory_management.service;

import com.gt.inventory_management.dto.LoginRequestDTO;
import com.gt.inventory_management.dto.LoginResponseDTO;
import com.gt.inventory_management.dto.RegisterRequestDTO;
import com.gt.inventory_management.exception.AuthenticationFailureException;
import com.gt.inventory_management.exception.InvalidInputException;
import com.gt.inventory_management.model.*;
import com.gt.inventory_management.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
    private AdminRepository adminRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private DealerRepository dealerRepository;

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

        if(request.getRole().equals("ADMIN")){
            Admin admin = Admin.builder()
                    .name(request.getName())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(role)
                    .mobileNo(request.getMobileNo())
                    .address(request.getAddress())
                    .status("ACTIVE")
                    .build();

            adminRepository.save(admin);
        } else if (request.getRole().equals("CUSTOMER")) {
            Customer customer= Customer.builder()
                    .name(request.getName())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(role)
                    .mobileNo(request.getMobileNo())
                    .address(request.getAddress())
                    .status("ACTIVE")
                    .build();

            customerRepository.save(customer);
        }
        else if (request.getRole().equals("DEALER")){
            Dealer dealer=Dealer.builder()
                    .name(request.getName())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(role)
                    .mobileNo(request.getMobileNo())
                    .address(request.getAddress())
                    .status("ACTIVE")
                    .build();
            dealerRepository.save(dealer);
        }

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
