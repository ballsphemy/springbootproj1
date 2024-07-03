package com.aclc.InventoryManagementSystem.service;

import com.aclc.InventoryManagementSystem.dto.UserDto;
import com.aclc.InventoryManagementSystem.mapper.UserMapper;
import com.aclc.InventoryManagementSystem.model.User;
import com.aclc.InventoryManagementSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::toDto).collect(Collectors.toList());
    }

    public UserDto getUserById(int id) {
        return userRepository.findById(id).map(userMapper::toDto).orElse(null);
    }

    public UserDto saveUser(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        user = userRepository.save(user);
        return userMapper.toDto(user);
    }

    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    public UserDto loginUser(String username, String password) {
        Optional<User> userOptional = userRepository.findByUsernameAndPassword(username, password);
        return userOptional.map(userMapper::toDto).orElse(null);
    }

    public UserDto updateUser(UserDto userDto) {
        // Check if the user exists
        Optional<User> optionalUser = userRepository.findById(userDto.getId());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            // Update user information
            user.setUsername(userDto.getUsername());
            user.setFirstname(userDto.getFirstname());
            user.setLastname(userDto.getLastname());
            // Save the updated user
            user = userRepository.save(user);
            return userMapper.toDto(user);
        } else {
            // User not found, throw exception or return null as per your requirement
            throw new RuntimeException("User not found with ID: " + userDto.getId());
        }
    }
}