package com.example.demo.controller;

import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestParam String username, @RequestParam String password) {
        try {
            userService.registerUser(username, password);
            return ResponseEntity.ok("User registered successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestParam String username, @RequestParam String password) {
        try {
            userService.authenticateUser(username, password);
            return ResponseEntity.ok("Login successful.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile(@RequestParam String username) {
        return userService.getUserProfile(username).
                map(x -> ResponseEntity.ok().body("user not found"))
                .orElseGet(() -> ResponseEntity.badRequest().body("User not found."));
    }

    @PostMapping("/images/upload")
    public ResponseEntity<String> addImageToUser(@RequestParam String username, @RequestParam String imageUrl) {
        try {
            userService.addImageToUser(username, imageUrl);
            return ResponseEntity.ok("Image added successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/images/delete")
    public ResponseEntity<String> removeImageFromUser(@RequestParam String username, @RequestParam String imageUrl) {
        try {
            userService.removeImageFromUser(username, imageUrl);
            return ResponseEntity.ok("Image removed successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

