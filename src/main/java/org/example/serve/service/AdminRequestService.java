package org.example.serve.service;

import org.example.serve.model.AdminRequest;
import org.example.serve.model.RequestStatus;
import org.example.serve.model.User;
import org.example.serve.repositories.AdminRequestRepository;
import org.example.serve.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.serve.model.Role;

import java.util.List;

@Service
public class AdminRequestService {

    @Autowired
    private AdminRequestRepository adminRequestRepository;

    @Autowired
    private UserRepository userRepository;

    public List<AdminRequest> getAllPendingRequests() {
        return adminRequestRepository.findByStatus(RequestStatus.PENDING);
    }

    // Updated to include the User parameter
    public void approveRequest(Long requestId, User user) {
        AdminRequest request = adminRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        request.setStatus(RequestStatus.APPROVED);
        adminRequestRepository.save(request);

        user.setRole(Role.ADMIN); // Set the user's role to ADMIN
        userRepository.save(user); // Save the user with the updated role
    }

    public void rejectRequest(Long requestId) {
        AdminRequest request = adminRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        request.setStatus(RequestStatus.REJECTED);
        adminRequestRepository.save(request);
    }
}
