package com.gt.inventory_management.config;

import com.gt.inventory_management.model.User;
import com.gt.inventory_management.repo.UserRepository;
import com.gt.inventory_management.service.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

       // System.out.println("Loaded user: " + user.getEmail() + " | ROLE_" + user.getRole().getName());

        return new CustomUserDetails(user);
    }
}
