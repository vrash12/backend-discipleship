package org.example.serve.repositories.faithtest;

import org.example.serve.model.FaithTestCompletion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.example.serve.model.User;
@Repository
public interface FaithTestCompletionRepository extends JpaRepository<FaithTestCompletion, Long> {
    boolean existsByUser(User user);
}
