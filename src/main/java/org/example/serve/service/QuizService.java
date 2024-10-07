package org.example.serve.service;

import org.example.serve.model.*;
import org.example.serve.repositories.quiz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuestionEnglishRepository questionEnglishRepository;

    @Autowired
    private QuestionTagalogRepository questionTagalogRepository;

    @Autowired
    private AnswerEnglishRepository answerEnglishRepository;

    @Autowired
    private AnswerTagalogRepository answerTagalogRepository;

    public List<QuestionEnglish> getEnglishQuestionsByQuizId(Long quizId) {
        return questionEnglishRepository.findByQuizId(quizId);
    }

    // In QuizService
    public List<QuestionTagalog> getTagalogQuestionsByQuizId(Long quizId) {
        return questionTagalogRepository.findByQuizId(quizId);
    }




    // Fetch Tagalog answers by question ID
    public List<AnswerTagalog> getTagalogAnswersByQuestionId(Long questionId) {
        return answerTagalogRepository.findByQuestionTagalogQuestionID(questionId);
    }

    public Optional<Quiz> getQuizById(Long quizId) {
        return quizRepository.findById(quizId);
    }


}
