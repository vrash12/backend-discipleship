package org.example.serve.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.serve.model.User;
import org.example.serve.model.Admin;  // Import Admin class
import org.example.serve.model.AdminRequest;
import org.example.serve.model.RequestStatus;
import org.example.serve.model.Role;
import org.example.serve.repositories.UserRepository;
import org.example.serve.repositories.AdminRequestRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.example.serve.service.UserService;
import java.util.Optional;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
@RestController
public class AuthController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private AdminRequestRepository adminRequestRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @PostMapping("/api/admin-signup")
    public ResponseEntity<String> adminSignUp(@RequestBody Admin admin) {
        // Admin signup logic
        Optional<User> existingUser = userRepository.findByEmail(admin.getEmail());
        if (existingUser.isPresent()) {
            return ResponseEntity.badRequest().body("Email is already in use.");
        }

        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        admin.setRole(Role.ADMIN);
        admin.setApproved(false);
        userRepository.save(admin);

        AdminRequest adminRequest = new AdminRequest();
        adminRequest.setUser(admin);
        adminRequest.setStatus(RequestStatus.PENDING);
        adminRequestRepository.save(adminRequest);

        return ResponseEntity.ok("Admin signup request submitted successfully. Awaiting approval.");
    }
    @GetMapping("/request-status/{requestId}")
    public ResponseEntity<?> getRequestDetails(@PathVariable Long requestId) {
        Optional<AdminRequest> adminRequest = adminRequestRepository.findById(requestId);
        if (adminRequest.isPresent()) {
            AdminRequest request = adminRequest.get();
            Map<String, Object> response = new HashMap<>();
            response.put("id", request.getId());
            response.put("user", request.getUser());
            response.put("status", request.getStatus());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(404).body("Request not found");
        }
    }


    // Endpoint to fetch all pending admin requests
    @GetMapping("/requests/pending")
    public ResponseEntity<List<AdminRequest>> getPendingAdminRequests() {
        List<AdminRequest> pendingRequests = adminRequestRepository.findByStatus(RequestStatus.PENDING);
        if (pendingRequests.isEmpty()) {
            System.out.println("DEBUG: No pending admin requests found.");
        } else {
            System.out.println("DEBUG: Pending admin requests count: " + pendingRequests.size());
        }
        return ResponseEntity.ok(pendingRequests);
    }


    @PostMapping("/requests/approve/{requestId}")
    public ResponseEntity<String> approveRequest(@PathVariable Long requestId) {
        // Log the requestId received to debug further
        System.out.println("DEBUG: Approve request called with ID: " + requestId);

        Optional<AdminRequest> adminRequestOptional = adminRequestRepository.findById(requestId);

        if (!adminRequestOptional.isPresent()) {
            System.out.println("DEBUG: Admin request with ID: " + requestId + " not found");
            return ResponseEntity.status(404).body("Request not found");
        }

        AdminRequest adminRequest = adminRequestOptional.get();
        adminRequest.setStatus(RequestStatus.APPROVED);
        adminRequestRepository.save(adminRequest);

        // Update the user to approved status if needed
        User adminUser = adminRequest.getUser();
        adminUser.setApproved(true);
        userRepository.save(adminUser);

        return ResponseEntity.ok("Request approved successfully");
    }
    @PostMapping("/requests/reject/{requestId}")
    public ResponseEntity<String> rejectRequest(@PathVariable Long requestId) {
        Optional<AdminRequest> adminRequestOptional = adminRequestRepository.findById(requestId);

        if (!adminRequestOptional.isPresent()) {
            return ResponseEntity.status(404).body("Request not found");
        }

        AdminRequest adminRequest = adminRequestOptional.get();
        adminRequest.setStatus(RequestStatus.REJECTED);
        adminRequestRepository.save(adminRequest);

        return ResponseEntity.ok("Request rejected successfully");
    }


    @PostMapping("/api/user-signup")
    public ResponseEntity<String> userSignUp(@RequestBody User user) {
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            return ResponseEntity.badRequest().body("Email is already in use.");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        user.setApproved(true);
        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully.");
    }


    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }


}
