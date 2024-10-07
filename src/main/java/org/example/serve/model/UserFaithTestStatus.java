package org.example.serve.model;

import jakarta.persistence.*;

@Entity
@Table(name = "user_faith_test_status")
public class UserFaithTestStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "test_id", nullable = false)
    private Long testId;

    @Column(name = "completed", nullable = false)
    private boolean completed;

    // Constructors
    public UserFaithTestStatus() {
    }

    public UserFaithTestStatus(Long userId, Long testId, boolean completed) {
        this.userId = userId;
        this.testId = testId;
        this.completed = completed;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTestId() {
        return testId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
