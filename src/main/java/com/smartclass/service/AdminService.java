package com.smartclass.service;

import com.smartclass.entity.Role;
import com.smartclass.entity.User;
import com.smartclass.payload.request.CreateUserRequest;
import com.smartclass.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    public void createUser(CreateUserRequest request) {
        if (userRepository.existsByName(request.getName())) {
            throw new RuntimeException("Error: Username is already taken!");
        }

        if (request.getEmail() != null && userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Error: Email is already in use!");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());

        // Generate a random default password e.g. SmartClass@123
        user.setPassword(encoder.encode("SmartClass@123"));

        user.setRole(Role.valueOf(request.getRole().toUpperCase()));
        user.setFirstLogin(true);

        userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
