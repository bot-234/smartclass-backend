package com.smartclass.component;

import com.smartclass.entity.Role;
import com.smartclass.entity.User;
import com.smartclass.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (!userRepository.existsByName("Unknown")) {
            User admin = new User();
            admin.setName("Unknown");
            admin.setPassword(passwordEncoder.encode("AdminPassword"));
            admin.setRole(Role.ADMIN);
            admin.setFirstLogin(true);
            userRepository.save(admin);
            System.out.println("Default Admin created: Unknown / AdminPassword");
        }
    }
}
