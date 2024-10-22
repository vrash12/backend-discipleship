// FeedbackRepository.java

package org.example.serve.repositories;

import org.example.serve.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    // Additional query methods can be defined here
}
