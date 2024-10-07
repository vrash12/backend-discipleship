package org.example.serve.repositories.quiz;

import org.example.serve.model.AnswerTagalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AnswerTagalogRepository extends JpaRepository<AnswerTagalog, Long> {


    List<AnswerTagalog> findByQuestionTagalogQuestionID(Long questionID);
}