package com.gt.inventory_management.mapper;

import com.gt.inventory_management.dto.UserDTO;
import com.gt.inventory_management.model.User;

public class UserMapper {

    public static UserDTO toDto(User user){

        UserDTO dto =new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setAddress(user.getAddress());
        dto.setEmail(user.getEmail());
        dto.setMobileNo(user.getMobileNo());
        if (user.getRole() != null) {
            dto.setRoleName(user.getRole().getName());
        }
        dto.setPassword(user.getPassword());
        return dto;
    }

    public static User toUser(UserDTO dto){
        User user=new User();
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setAddress(dto.getAddress());
        user.setMobileNo(dto.getMobileNo());
        user.setPassword(dto.getPassword());
        return user;
    }

}
