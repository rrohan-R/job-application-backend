package com.project.JobApplication.controller;

import com.project.JobApplication.model.Users;
import com.project.JobApplication.repository.UserRepository;
import com.project.JobApplication.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/signup")
    public Map<String, String> signup(@RequestBody Users user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");
        repository.save(user);

        String token = jwtUtil.generateToken(user.getUsername());
        return Map.of("token", token);
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> credentials) {
        manager.authenticate(new UsernamePasswordAuthenticationToken(
                credentials.get("username"), credentials.get("password")));

        Users user = repository.findByUsername(credentials.get("username")).orElseThrow();

        String token = jwtUtil.generateToken(user.getUsername());

        return Map.of(
                "token", token,
                "username", user.getUsername(),
                "role", user.getRole()
        );
    }


}

