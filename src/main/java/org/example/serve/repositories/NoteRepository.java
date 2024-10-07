package org.example.serve.repositories;



import org.example.serve.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    // You can define custom query methods here if needed
}

