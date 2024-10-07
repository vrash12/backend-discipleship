package org.example.serve.controllers;

import org.example.serve.model.AdminRequest;
import org.example.serve.model.User;
import org.example.serve.service.AdminRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.example.serve.model.RequestStatus;
import org.example.serve.repositories.AdminRequestRepository;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminRequestRepository adminRequestRepository;

    @Autowired
    private AdminRequestService adminRequestService;

    // Endpoint to view the Admin Dashboard
    @GetMapping("/dashboard")
    public ResponseEntity<String> getAdminDashboard() {
        return ResponseEntity.ok("Welcome to the Admin Dashboard");
    }

    // Endpoint to fetch all pending admin requests
    @GetMapping("/requests/pending")
    public ResponseEntity<List<AdminRequest>> getPendingAdminRequests() {
        List<AdminRequest> pendingRequests = adminRequestRepository.findByStatus(RequestStatus.PENDING);
        if (pendingRequests.isEmpty()) {
            return ResponseEntity.noContent().build(); // Returns 204 if no content
        }
        return ResponseEntity.ok(pendingRequests);
    }


    // Endpoint to approve an admin request
    @PostMapping("/requests/approve/{id}")
    public ResponseEntity<String> approveAdminRequest(@PathVariable Long id) {
        try {
            User requester = getAuthenticatedUser();
            adminRequestService.approveRequest(id, requester); // Ensure that approveRequest takes both the request ID and the requester
            return ResponseEntity.ok("Admin request approved successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error approving request: " + e.getMessage());
        }
    }

    // Endpoint to reject an admin request
    @PostMapping("/requests/reject/{id}")
    public ResponseEntity<String> rejectAdminRequest(@PathVariable Long id) {
        try {
            User requester = getAuthenticatedUser();
            adminRequestService.rejectRequest(id); // Pass only the request ID for rejection
            return ResponseEntity.ok("Admin request rejected successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error rejecting request: " + e.getMessage());
        }
    }

    // Helper method to get the current authenticated user
    private User getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (User) auth.getPrincipal();
    }
}
