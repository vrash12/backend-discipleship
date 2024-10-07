package org.example.serve;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.example.serve.model.User;
import org.example.serve.repositories.UserRepository;
import org.example.serve.model.Role;
import org.example.serve.model.RequestStatus;
import org.example.serve.model.AdminRequest;
import org.example.serve.repositories.AdminRequestRepository;

@SpringBootApplication
public class ServeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServeApplication.class, args);
    }

    @Bean
    CommandLineRunner init(UserRepository userRepository, PasswordEncoder passwordEncoder, AdminRequestRepository adminRequestRepository) {
        return args -> {
            // Check and create HEAD_ADMIN user if not present
            if (!userRepository.findByEmail("headadmin@gmail.com").isPresent()) {
                User headAdmin = new User();
                headAdmin.setEmail("headadmin@gmail.com");
                headAdmin.setName("Head Admin");
                headAdmin.setPassword(passwordEncoder.encode("headadmin")); // Encrypting the password
                headAdmin.setRole(Role.HEAD_ADMIN); // Setting role to HEAD_ADMIN
                userRepository.save(headAdmin);
            }

            // Check and create ADMIN user if not present
            if (!userRepository.findByEmail("admin@gmail.com").isPresent()) {
                User admin = new User();
                admin.setEmail("admin@gmail.com");
                admin.setName("Admin User");
                admin.setPassword(passwordEncoder.encode("admin")); // Encrypting the password
                admin.setRole(Role.USER); // Initially set to USER until approved

                // Save user as a normal user initially
                userRepository.save(admin);

                // Create an admin request for the new ADMIN user
                AdminRequest adminRequest = new AdminRequest();
                adminRequest.setUser(admin);
                adminRequest.setStatus(RequestStatus.PENDING); // Set the request status to PENDING

                // Save the admin request to the repository
                adminRequestRepository.save(adminRequest);
            }
        };
    }
}
