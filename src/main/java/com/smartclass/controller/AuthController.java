package com.smartclass.controller;

import com.smartclass.entity.User;
import com.smartclass.payload.request.ChangePasswordRequest;
import com.smartclass.payload.request.LoginRequest;
import com.smartclass.payload.response.JwtResponse;
import com.smartclass.payload.response.MessageResponse;
import com.smartclass.repository.UserRepository;
import com.smartclass.security.JwtUtils;
import com.smartclass.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        System.out.println("Login API HIT");
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getName(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            String role = userDetails.getAuthorities().iterator().next().getAuthority().replace("ROLE_", "");

            return ResponseEntity.ok(new JwtResponse(jwt,
                    userDetails.getId(),
                    userDetails.getUsername(),
                    userDetails.getEmail(),
                    role,
                    userDetails.isFirstLogin()));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(new MessageResponse("Error: Unauthorized"));
        }
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request, Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Unauthorized."));
        }

        String username = authentication.getName();
        Optional<User> userOpt = userRepository.findByName(username);
        
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setPassword(encoder.encode(request.getNewPassword()));
            user.setFirstLogin(false);
            userRepository.save(user);
            return ResponseEntity.ok(new MessageResponse("Password updated successfully!"));
        }

        return ResponseEntity.badRequest().body(new MessageResponse("Error: User not found."));
    }
}
