package com.example.casinomoney.controller;

import com.example.casinomoney.model.User;
import com.example.casinomoney.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", allowCredentials = "true")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // GET: Get user by id
    @GetMapping("/{email}")
    public ResponseEntity<User> getUserById(@PathVariable String email) {
        Optional<User> userOptional = userRepository.findAll().stream().filter(user -> user.getEmail().equals(email)).findAny();

        return userOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST: Create a new user
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User userToSave) {
        // Check if a user with the provided email already exists
        Optional<User> existingUser = userRepository.findAll().stream().filter(user -> user.getEmail().equals(userToSave.getEmail())).findAny();

        if (existingUser.isPresent()) {
            // If user exists, return 200 OK with the existing user
            return ResponseEntity.ok(existingUser.get());
        } else {
            // If user does not exist, save the new user
            User newUser = userRepository.save(userToSave);
            return ResponseEntity.status(201).body(newUser); // Return 201 Created
        }
    }


    // PUT: Deposit to user balance by ID
    @PutMapping("/{email}/deposit")
    public ResponseEntity<User> depositToBalance(@PathVariable String email, @RequestBody Integer amount) {
        Optional<User> userOptional = userRepository.findAll().stream().filter(user -> user.getEmail().equals(email)).findAny();

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setMoneyAmount(user.getMoneyAmount() + amount); // Add deposit amount
            User updatedUser = userRepository.save(user);
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build(); // User not found
        }
    }

    // PUT: Withdraw from user balance by ID
    @PutMapping("/{email}/withdraw")
    public ResponseEntity<User> withdrawFromBalance(@PathVariable String email, @RequestBody Integer amount) {
        Optional<User> userOptional = userRepository.findAll().stream().filter(user -> user.getEmail().equals(email)).findAny();

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getMoneyAmount() >= amount) {
                user.setMoneyAmount(user.getMoneyAmount() - amount); // Subtract withdrawal amount
                User updatedUser = userRepository.save(user);
                return ResponseEntity.ok(updatedUser);
            } else {
                return ResponseEntity.badRequest().body(null); // Insufficient funds
            }
        } else {
            return ResponseEntity.notFound().build(); // User not found
        }
    }
}
