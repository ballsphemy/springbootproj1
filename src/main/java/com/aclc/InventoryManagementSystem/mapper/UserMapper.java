package com.aclc.InventoryManagementSystem.mapper;

import com.aclc.InventoryManagementSystem.dto.UserDto;
import com.aclc.InventoryManagementSystem.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDto toDto(User user) {
        return new UserDto(user.getId(), user.getUsername(), user.getFirstname(), user.getLastname(), user.getRole());
    }

    public User toEntity(UserDto userDto) {
        return new User(userDto.getUsername(), userDto.getPassword(), userDto.getFirstname(), userDto.getLastname(), userDto.getRole());
    }
}