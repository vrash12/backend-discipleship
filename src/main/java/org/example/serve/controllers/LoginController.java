package org.example.serve.controllers;

import org.example.serve.model.User;
import org.example.serve.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import org.example.serve.model.Role;
import org.example.serve.model.LoginRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.example.serve.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.example.serve.model.LoginResponse;
import java.util.Map;
import org.example.serve.model.User;


@RestController
@RequestMapping("/api/auth")
public class LoginController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/signup_user")
    public ResponseEntity<?> signup(@RequestBody User signupRequest) {
        System.out.println("DEBUG: Received signup request for email: " + signupRequest.getEmail());

        if (userService.findByEmail(signupRequest.getEmail()).isPresent()) {
            System.out.println("DEBUG: Email already in use: " + signupRequest.getEmail());
            return ResponseEntity.status(400).body("Email already in use");
        }

        try {
            // Encode password before saving
            String encodedPassword = passwordEncoder.encode(signupRequest.getPassword());
            signupRequest.setPassword(encodedPassword);

            // Set the approval status based on the role
            if (signupRequest.getRole() == Role.ADMIN) {
                signupRequest.setApproved(false);  // Admin signup requires approval
            } else {
                signupRequest.setApproved(true);   // Regular users are approved by default
            }

            System.out.println("DEBUG: Encoded password during signup: " + encodedPassword);
            userService.saveUser(signupRequest);
            System.out.println("DEBUG: User registered successfully with email: " + signupRequest.getEmail());

            if (signupRequest.getRole() == Role.ADMIN) {
                return ResponseEntity.ok("Admin signup request submitted. Awaiting approval.");
            } else {
                return ResponseEntity.ok("User registered successfully");
            }
        } catch (Exception e) {
            System.out.println("ERROR: Error during signup: " + e.getMessage());
            return ResponseEntity.status(400).body("Signup failed due to server error");
        }
    }


    @PostMapping("/signup_admin")
    public ResponseEntity<?> signupAdmin(@RequestBody User signupRequest) {
        System.out.println("DEBUG: Received signup request for admin email: " + signupRequest.getEmail());

        if (userService.findByEmail(signupRequest.getEmail()).isPresent()) {
            System.out.println("DEBUG: Email already in use: " + signupRequest.getEmail());
            return ResponseEntity.status(400).body("Email already in use");
        }

        try {
            // Encode password before saving
            String encodedPassword = passwordEncoder.encode(signupRequest.getPassword());
            signupRequest.setPassword(encodedPassword);
            System.out.println("DEBUG: Encoded password during signup: " + encodedPassword);

            // Set role to ADMIN
            signupRequest.setRole(Role.ADMIN);
            signupRequest.setApproved(false); // New admin users are not approved by default

            userService.saveUser(signupRequest);
            System.out.println("DEBUG: Admin signup request submitted successfully for email: " + signupRequest.getEmail());
            return ResponseEntity.ok("Admin signup request submitted successfully. Awaiting approval.");
        } catch (Exception e) {
            System.out.println("ERROR: Error during admin signup: " + e.getMessage());
            return ResponseEntity.status(400).body("Admin signup failed due to server error");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            Optional<User> userOptional = userService.findByEmail(loginRequest.getEmail());
            if (userOptional.isPresent()) {
                User user = userOptional.get();

                if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                    if (user.getRole() == Role.ADMIN && !user.isApproved()) {
                        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Your account is pending approval by the head admin.");
                    }

                    // Include user ID in the response
                    LoginResponse loginResponse = new LoginResponse(user.getRole().toString(), user.getName(), user.getId());
                    return ResponseEntity.ok(loginResponse);

                } else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password.");
                }

            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
            }

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password.");
        }
    }



    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        System.out.println("DEBUG: Getting user by ID: " + id);
        Optional<User> userOptional = userService.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // For security, do not include the password in the response
            user.setPassword(null);
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
    }

    // Update user profile
    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUserProfile(@PathVariable Long id, @RequestBody User updatedUser) {
        System.out.println("DEBUG: Updating user profile for ID: " + id);
        Optional<User> userOptional = userService.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // Update user data
            user.setName(updatedUser.getName());
            user.setEmail(updatedUser.getEmail());
            userService.saveUser(user);
            return ResponseEntity.ok("User profile updated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
    }

    // Change user password
    @PutMapping("/users/{id}/change-password")
    public ResponseEntity<?> changePassword(@PathVariable Long id, @RequestBody Map<String, String> request) {
        System.out.println("DEBUG: Changing password for user ID: " + id);
        Optional<User> userOptional = userService.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String newPassword = request.get("password");
            if (newPassword != null && !newPassword.isEmpty()) {
                String encodedPassword = passwordEncoder.encode(newPassword);
                user.setPassword(encodedPassword);
                userService.saveUser(user);
                return ResponseEntity.ok("Password changed successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password cannot be empty.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
    }
}
