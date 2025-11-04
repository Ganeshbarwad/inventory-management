package com.gt.inventory_management.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequestDTO {
    private String name;
    private String email;
    private String password;
    private String role;
    private String mobileNo;
    private String address;
}

