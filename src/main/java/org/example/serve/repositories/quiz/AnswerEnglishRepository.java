package org.example.serve.repositories.quiz;

import org.example.serve.model.AnswerEnglish;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AnswerEnglishRepository extends JpaRepository<AnswerEnglish, Long> {
    List<AnswerEnglish> findByQuestionQuestionID(Long questionID);
}
