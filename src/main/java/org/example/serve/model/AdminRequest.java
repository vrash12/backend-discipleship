package org.example.serve.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "admin_requests")
public class AdminRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // Ensure the column name matches the foreign key in the database
    private User user; // Change to User

    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    // Default constructor
    public AdminRequest() {
    }

    // Parameterized constructor
    public AdminRequest(User user, RequestStatus status) { // Change to User
        this.user = user; // Change to User
        this.status = status;
        this.createdAt = LocalDateTime.now(); // Set to current time
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() { // Change to User
        return user;
    }

    public void setUser(User user) { // Change to User
        this.user = user;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
