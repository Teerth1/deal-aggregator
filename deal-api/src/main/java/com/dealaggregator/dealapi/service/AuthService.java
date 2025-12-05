package com.dealaggregator.dealapi.service;

import org.springframework.stereotype.Service;
import com.dealaggregator.dealapi.repository.UserRepository;
import com.dealaggregator.dealapi.entity.User;



@Service
public class AuthService {
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(String username, String password, String email) {
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Username is already taken!");
        } 

        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email is already in use!");
        } 

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(password);
        return userRepository.save(newUser);
    }


    public User login(String username, String password) {
        User user  = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid credentials!");
        } 
        return user;
    }

   

}