package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(String username, String password) throws IllegalArgumentException {
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username is already taken.");
        }
        User user = new User(username, passwordEncoder.encode(password));
        return userRepository.save(user);
    }

    public User authenticateUser(String username, String password) {
        Optional<User> optionalUser = Optional.ofNullable(userRepository.findByUsername(username));
        if (optionalUser.isPresent() && passwordEncoder.matches(password, optionalUser.get().getPassword())) {
            return optionalUser.get();
        }
        throw new IllegalArgumentException("Invalid username or password.");
    }

    public Optional<User> getUserProfile(String username) {
        return Optional.ofNullable(userRepository.findByUsername(username));
    }

    public void addImageToUser(String username, String imageUrl) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new IllegalArgumentException("User not found.");
        }
        user.addImageUrl(imageUrl);
        userRepository.save(user);
    }

    public void removeImageFromUser(String username, String imageUrl) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new IllegalArgumentException("User not found.");
        }
        user.removeImageUrl(imageUrl);
        userRepository.save(user);
    }
}

