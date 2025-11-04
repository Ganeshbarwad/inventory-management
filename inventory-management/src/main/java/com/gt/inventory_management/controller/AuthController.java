package com.gt.inventory_management.controller;

import com.gt.inventory_management.dto.LoginRequestDTO;
import com.gt.inventory_management.dto.LoginResponseDTO;
import com.gt.inventory_management.dto.RegisterRequestDTO;
import com.gt.inventory_management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequestDTO request) {
        String message = userService.registerUser(request);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request) {
        return ResponseEntity.ok(userService.login(request));
    }
}
