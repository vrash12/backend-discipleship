// UserController.java

package org.example.serve.controllers;

import org.example.serve.model.User;
import org.example.serve.model.Role;
import org.example.serve.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.List;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/users")  // Base URL for all user-related endpoints
public class UserController {

    @Autowired
    private UserRepository userRepository;


    @GetMapping
    public ResponseEntity<?> getUsers(
            @RequestParam(required = false) String role,
            @RequestParam(required = false) Boolean approved
    ) {
        try {
            List<User> users;

            if (role != null && approved != null) {
                // Fetch users by role and approval status
                Role enumRole = Role.valueOf(role.toUpperCase());
                users = userRepository.findByRoleAndApproved(enumRole, approved);
            } else if (role != null) {
                // Fetch users by role
                Role enumRole = Role.valueOf(role.toUpperCase());
                users = userRepository.findByRole(enumRole);
            } else if (approved != null) {
                // Fetch users by approval status
                users = userRepository.findByApproved(approved);
            } else {
                // Fetch all users
                users = userRepository.findAll();
            }

            return ResponseEntity.ok(users);
        } catch (IllegalArgumentException e) {
            // Handle invalid role values
            return ResponseEntity.badRequest().body("Invalid role parameter.");
        } catch (Exception e) {
            // Handle other exceptions
            e.printStackTrace();
            return ResponseEntity.status(500).body("Internal Server Error.");
        }
    }


    @PostMapping("/{id}/activate")
    public ResponseEntity<?> activateUser(@PathVariable Long id) {
        Optional<User> userOpt = userRepository.findById(id);

        if (!userOpt.isPresent()) {
            return ResponseEntity.status(404).body("User not found.");
        }

        User user = userOpt.get();
        user.setApproved(true);
        userRepository.save(user);

        return ResponseEntity.ok("User activated successfully.");
    }


    @PostMapping("/{id}/deactivate")
    public ResponseEntity<?> deactivateUser(@PathVariable Long id) {
        Optional<User> userOpt = userRepository.findById(id);

        if (!userOpt.isPresent()) {
            return ResponseEntity.status(404).body("User not found.");
        }

        User user = userOpt.get();
        user.setApproved(false);
        userRepository.save(user);

        return ResponseEntity.ok("User deactivated successfully.");
    }


    @PostMapping("/{id}/assign-leader")
    public ResponseEntity<?> assignLeader(
            @PathVariable Long id,
            @RequestParam Long leaderId
    ) {
        // Prevent assigning self as leader
        if (id.equals(leaderId)) {
            return ResponseEntity.badRequest().body("A user cannot be their own leader.");
        }

        try {
            Optional<User> userOpt = userRepository.findById(id);
            Optional<User> leaderOpt = userRepository.findById(leaderId);

            if (!userOpt.isPresent()) {
                return ResponseEntity.status(404).body("User not found.");
            }

            if (!leaderOpt.isPresent()) {
                return ResponseEntity.status(404).body("Leader not found.");
            }

            User user = userOpt.get();
            User leader = leaderOpt.get();

            // Optional: Ensure the leader is approved
            if (!leader.isApproved()) {
                return ResponseEntity.badRequest().body("Leader must be an approved user.");
            }

            // Assign the leader
            user.setLeader(leader);
            userRepository.save(user);

            return ResponseEntity.ok("Leader assigned successfully.");
        } catch (Exception e) {
            // Log the exception details for debugging
            e.printStackTrace();
            return ResponseEntity.status(500).body("An error occurred while assigning the leader.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(
            @PathVariable Long id,
            @RequestBody User updatedUser
    ) {
        Optional<User> userOpt = userRepository.findById(id);

        if (!userOpt.isPresent()) {
            return ResponseEntity.status(404).body("User not found.");
        }

        User user = userOpt.get();

        // Update fields if provided
        if (updatedUser.getName() != null && !updatedUser.getName().isEmpty()) {
            user.setName(updatedUser.getName());
        }

        if (updatedUser.getEmail() != null && !updatedUser.getEmail().isEmpty()) {
            // Optionally, check if the new email is already in use
            Optional<User> emailCheck = userRepository.findByEmail(updatedUser.getEmail());
            if (emailCheck.isPresent() && !emailCheck.get().getId().equals(id)) {
                return ResponseEntity.badRequest().body("Email is already in use.");
            }
            user.setEmail(updatedUser.getEmail());
        }


        userRepository.save(user);

        return ResponseEntity.ok("User updated successfully.");
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        Optional<User> userOpt = userRepository.findById(id);

        if (!userOpt.isPresent()) {
            return ResponseEntity.status(404).body("User not found.");
        }

        userRepository.delete(userOpt.get());

        return ResponseEntity.ok("User deleted successfully.");
    }

    @PostMapping("/admin-signup")
    public ResponseEntity<?> adminSignup(@RequestBody User user) {
        // Check if email already exists
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already exists.");
        }
        // Set user role and other default values
        user.setRole(Role.ADMIN);
        user.setApproved(false); // Admin approval required
        userRepository.save(user);
        return ResponseEntity.ok("Admin signup request submitted successfully. Awaiting approval.");
    }

    @PostMapping("/user-signup")
    public ResponseEntity<?> userSignup(@RequestBody User user) {
        // Check if email already exists
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already exists.");
        }
        // Set user role and other default values
        user.setRole(Role.USER);
        user.setApproved(true); // Automatically approve regular users
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully.");
    }
}
