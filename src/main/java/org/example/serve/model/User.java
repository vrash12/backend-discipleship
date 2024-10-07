package org.example.serve.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users") // Root class in the hierarchy declares the table mapping
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String password;
    private boolean approved = false;

    @Column(nullable = false)
    private Boolean hasCompletedFaithTest = false;

    @Enumerated(EnumType.STRING)
    private Role role;



    public User() {
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    // Getters and Setters...

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }
    public Boolean getHasCompletedFaithTest() {
        return hasCompletedFaithTest;
    }

    public void setHasCompletedFaithTest(Boolean hasCompletedFaithTest) {
        this.hasCompletedFaithTest = hasCompletedFaithTest;
    }

}
