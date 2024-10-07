package org.example.serve.repositories.faithtest;

import org.example.serve.model.UserFaithTestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

public interface UserFaithTestStatusRepository extends JpaRepository<UserFaithTestStatus, Long> {

    // Method to find the status of a user's faith test by user ID and test ID
    Optional<UserFaithTestStatus> findByUserIdAndTestId(Long userId, Long testId);
    // Add this method to your `UserFaithTestStatusRepository`
    List<UserFaithTestStatus> findByCompleted(boolean completed);



}