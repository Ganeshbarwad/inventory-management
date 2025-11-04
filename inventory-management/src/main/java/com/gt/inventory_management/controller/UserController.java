package com.gt.inventory_management.controller;


import com.gt.inventory_management.model.User;
import com.gt.inventory_management.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserRepository userRepo;

    @GetMapping("/all")
    public List<User> getAllUsers(){

        return userRepo.findAll();
    }
}
