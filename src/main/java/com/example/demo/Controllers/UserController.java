package com.example.demo.Controllers;

import com.example.demo.Models.User;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            User savedUser = userService.registerUser(user);
            return new ResponseEntity<>(savedUser, HttpStatus.CREATED); // Return 201 status if successful
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception
            return new ResponseEntity<>("Error occurred while registering user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        // Simple login check (without bcrypt or JWT)
        User user = userService.loginUser(username, password);
        if (user != null) {
            return new ResponseEntity<>("Login successful", HttpStatus.OK); // Return success if login is successful
        } else {
            return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED); // Return 401 if credentials are invalid
        }
    }
}
