package com.dealaggregator.dealapi.controller;

import com.dealaggregator.dealapi.entity.User;
import com.dealaggregator.dealapi.service.AuthService;

import org.springframework.web.bind.annotation.*;
import lombok.Data;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/register")
    public User register(@RequestBody RegisterRequest request) {
        return authService.registerUser(request.getUsername(), request.getPassword(), request.getEmail());
    }

    @PostMapping("/login")
    public User login(@RequestBody LoginRequest request) {
        return authService.login(request.getUsername(),request.getPassword());
    }
    
    @Data
    public static class RegisterRequest {
        private String password;
        private String username;
        private String email;
    }

    @Data
    public static class LoginRequest {
        private String username;
        private String password;
    }

}
