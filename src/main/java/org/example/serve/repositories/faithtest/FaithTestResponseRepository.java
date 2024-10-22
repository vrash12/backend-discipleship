package org.example.serve.repositories.faithtest;

import org.example.serve.model.FaithTestResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.example.serve.model.User;
import java.util.Optional;


@Repository
public interface FaithTestResponseRepository extends JpaRepository<FaithTestResponse, Long> {

    // Method to find responses by user ID
    List<FaithTestResponse> findByUser_Id(Long userId);


    // Add this method to your `FaithTestResponseRepository`
    List<FaithTestResponse> findByUser(User user);

    // Method to check if the user has completed any tests
    boolean existsByUser_Id(Long userId);  // This checks if there are any responses for the user
    // Add this method to your `FaithTestResponseRepository`
    Optional<FaithTestResponse> findById(Long responseId);

    int countByUserId(Long userId);


}
