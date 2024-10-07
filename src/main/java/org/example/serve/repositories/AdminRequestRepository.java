package org.example.serve.repositories;

import org.example.serve.model.AdminRequest;
import org.example.serve.model.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.example.serve.model.User;
import java.util.Optional;
import java.util.List;

public interface AdminRequestRepository extends JpaRepository<AdminRequest, Long> {
    List<AdminRequest> findByStatus(RequestStatus status);
    Optional<AdminRequest> findByUser(User user);
    //adminRequestRepository
}