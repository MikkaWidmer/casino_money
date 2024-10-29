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
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // GET: Get all users
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // POST: Create a new user
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    // PUT: Update user moneyAmount by ID
    @PutMapping("/{id}/moneyAmount")
    public ResponseEntity<User> updateMoneyAmount(@PathVariable Long id, @RequestBody Integer moneyAmount) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setMoneyAmount(moneyAmount); // Update the moneyAmount
            User updatedUser = userRepository.save(user);
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build(); // User not found
        }
    }
}
