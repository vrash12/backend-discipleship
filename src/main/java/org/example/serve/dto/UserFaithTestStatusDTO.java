package org.example.serve.dto;

public class UserFaithTestStatusDTO {
    private Long id;
    private String name;
    private String email;
    private boolean completed;

    // Constructors
    public UserFaithTestStatusDTO() {
    }

    public UserFaithTestStatusDTO(Long id, String name, String email, boolean completed) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.completed = completed;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    // ... (Include all getters and setters for each field)
    public void setId(Long id) {
        this.id = id;
    }

    // Include getters and setters for 'name', 'email', and 'completed'
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
