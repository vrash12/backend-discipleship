package org.example.serve.model;

public class LoginResponse {
    private String role;
    private String name;
    private Long id;  // Add the user ID field

    public LoginResponse(String role, String name, Long id) {
        this.role = role;
        this.name = name;
        this.id = id;  // Initialize user ID
    }

    // Getters and setters for all fields
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

