package com.example.demo.services;

import com.example.demo.Models.User;
import com.example.demo.repositories.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User registerUser(User user) {
        try {
            // Ensure user details are not null
            if (user.getUsername() == null || user.getPassword() == null) {
                throw new IllegalArgumentException("Username or Password cannot be null");
            }
            // Save the user
            return userRepository.save(user);
        } catch (Exception e) {
            // Log any errors that occur during registration
            e.printStackTrace();
            throw new RuntimeException("Error while registering user");
        }
    }

    public User loginUser(String username, String password) {
        try {
            User user = userRepository.findByUsername(username);
            if (user != null && user.getPassword().equals(password)) {
                return user;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error while logging in");
        }
    }

    
}
