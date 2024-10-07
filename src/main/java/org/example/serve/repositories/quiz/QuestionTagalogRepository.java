package org.example.serve.repositories.quiz;

import org.example.serve.model.QuestionTagalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface QuestionTagalogRepository extends JpaRepository<QuestionTagalog, Long> {

    // In QuestionTagalogRepository
    List<QuestionTagalog> findByQuizId(Long quizId);


}