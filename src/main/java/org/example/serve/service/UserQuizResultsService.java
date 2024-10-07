package org.example.serve.service;

import org.example.serve.model.UserQuizResult;
import org.example.serve.model.User;
import org.example.serve.model.Quiz;
import org.example.serve.model.QuizStatus;
import org.example.serve.repositories.UserRepository;
import org.example.serve.repositories.quiz.QuizRepository;
import org.example.serve.repositories.quiz.UserQuizResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserQuizResultsService {

    private final UserQuizResultRepository userQuizResultRepository;
    private final UserRepository userRepository;
    private final QuizRepository quizRepository;

    @Autowired
    public UserQuizResultsService(UserQuizResultRepository userQuizResultRepository,
                                  UserRepository userRepository,
                                  QuizRepository quizRepository) {
        this.userQuizResultRepository = userQuizResultRepository;
        this.userRepository = userRepository;
        this.quizRepository = quizRepository;
    }

    public UserQuizResult saveQuizResult(Long userId, Long quizId, int score, int totalQuestions) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid quiz ID"));

        UserQuizResult result = new UserQuizResult();
        result.setUser(user);
        result.setQuiz(quiz);
        result.setScore(score);
        result.setTotalQuestions(totalQuestions);
        result.setSubmissionDate(LocalDateTime.now());
        result.setStatus(QuizStatus.COMPLETED);

        return userQuizResultRepository.save(result);
    }

    public List<UserQuizResult> getResultsByUserId(Long userId) {
        return userQuizResultRepository.findByUser_Id(userId);
    }

    public List<UserQuizResult> getResultsByQuizId(Long quizId) {
        return userQuizResultRepository.findByQuiz_Id(quizId);
    }

    public boolean hasUserSubmittedQuiz(Long userId, Long quizId) {
        return userQuizResultRepository.existsByUser_IdAndQuiz_IdAndStatus(userId, quizId, QuizStatus.COMPLETED);
    }

    public List<Long> getCompletedQuizIdsByUserId(Long userId) {
        return userQuizResultRepository.findQuizIdsByUserId(userId);
    }

    @Transactional
    public void resetQuizResult(Long userId, Long quizId) {
        userQuizResultRepository.deleteByUser_IdAndQuiz_Id(userId, quizId);
    }
}
