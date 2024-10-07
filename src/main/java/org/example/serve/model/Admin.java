package org.example.serve.model;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends User {

    public Admin() {
        super();
    }

    public Admin(String name, String email, String password) {
        super(name, email); // Calls the User constructor
        this.setPassword(password);
        this.setRole(Role.ADMIN); // Set role to ADMIN
        this.setApproved(false); // New admins are not approved by default
    }
}
