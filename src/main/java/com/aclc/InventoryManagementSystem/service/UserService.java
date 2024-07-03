package com.aclc.InventoryManagementSystem.service;

import com.aclc.InventoryManagementSystem.dto.UserDto;

import java.util.List;

public interface UserService {

    public List<UserDto> getAllUsers();
    public UserDto getUserById(int id);
    public UserDto saveUser(UserDto userDto);
    public void deleteUser(int id);
    public UserDto loginUser(String username, String password);
    public UserDto updateUser(UserDto userDto);
}
