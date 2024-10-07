package org.example.serve.repositories.quiz;

import org.example.serve.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    // Basic CRUD operations provided by JpaRepository

}
