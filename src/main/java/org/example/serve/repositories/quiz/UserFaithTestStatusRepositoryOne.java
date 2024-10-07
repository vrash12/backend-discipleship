package org.example.serve.repositories.quiz;

import org.example.serve.model.UserFaithTestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserFaithTestStatusRepositoryOne extends JpaRepository<UserFaithTestStatus, Long> {
    Optional<UserFaithTestStatus> findByUserId(Long userId);
}