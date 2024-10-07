package org.example.serve.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.serve.model.Sermon;

public interface SermonRepository extends JpaRepository<Sermon, Long> {
}
