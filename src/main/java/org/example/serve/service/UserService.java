package org.example.serve.service;

import org.example.serve.model.User;
import org.example.serve.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Optional<User> findByEmail(String email) {
        System.out.println("DEBUG: Finding user by email: " + email);
        return userRepository.findByEmail(email);
    }

    public User saveUser(User user) {
        // Password should be encoded outside of this service method (in the controller)
        System.out.println("DEBUG: Saving user with encoded password.");
        return userRepository.save(user);
    }

    public User findUserById(Long id) {
        System.out.println("DEBUG: Finding user by ID: " + id);
        return userRepository.findById(id).orElse(null);
    }

    public List<User> getAllUsers() {
        System.out.println("DEBUG: Retrieving all users");
        return userRepository.findAll();
    }

    public boolean markFaithTestAsCompleted(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setHasCompletedFaithTest(true);  // Assuming this field exists in your User model
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId);
    }
}
