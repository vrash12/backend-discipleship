package org.example.serve.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.serve.model.Lesson;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    // Custom query methods if needed
}