// Feedback.java

package org.example.serve.model;

import jakarta.persistence.*;


@Entity
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userEmail; // Optional: if you want to capture user's email
    private String message;

    // Constructors
    public Feedback() {}

    public Feedback(String userEmail, String message) {
        this.userEmail = userEmail;
        this.message = message;
    }

    // Getters and Setters
    // ...

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
