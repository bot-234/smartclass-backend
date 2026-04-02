package com.smartclass.service;

import com.smartclass.entity.Role;
import com.smartclass.entity.User;
import com.smartclass.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getStudentUsers() {
        return userRepository.findByRole(Role.STUDENT);
    }
}
