package org.example.serve.repositories.quiz;

import org.example.serve.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    // Corrected method
    List<Question> findAllByQuizId(Long quizId);

}
