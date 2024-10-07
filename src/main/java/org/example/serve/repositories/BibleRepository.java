package org.example.serve.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.serve.model.Bible;

public interface BibleRepository extends JpaRepository<Bible, Long> {
    // Custom query methods if needed
}