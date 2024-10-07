package org.example.serve.repositories.quiz;

import org.example.serve.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    // This method finds answers by the associated question's ID
    List<Answer> findByQuestionQuestionID(Long questionId);
}
