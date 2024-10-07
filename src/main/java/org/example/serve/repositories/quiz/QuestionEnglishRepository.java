package org.example.serve.repositories.quiz;

import org.example.serve.model.QuestionEnglish;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QuestionEnglishRepository extends JpaRepository<QuestionEnglish, Long> {
    List<QuestionEnglish> findByQuizId(Long quizId);
}
