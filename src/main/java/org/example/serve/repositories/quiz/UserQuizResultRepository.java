package org.example.serve.repositories.quiz;

import org.example.serve.model.UserQuizResult;
import org.example.serve.model.QuizStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface UserQuizResultRepository extends JpaRepository<UserQuizResult, Long> {

    // Find all quiz results by user ID
    List<UserQuizResult> findByUser_Id(Long userId);

    // Find all quiz results by quiz ID
    List<UserQuizResult> findByQuiz_Id(Long quizId);

    // Check if the user has already taken the quiz and the quiz is completed
    boolean existsByUser_IdAndQuiz_IdAndStatus(Long userId, Long quizId, QuizStatus status);

    // Find all quiz IDs that a user has completed
    @Query("SELECT uqr.quiz.id FROM UserQuizResult uqr WHERE uqr.user.id = :userId")
    List<Long> findQuizIdsByUserId(@Param("userId") Long userId);

    // Delete the quiz result based on user and quiz
    void deleteByUser_IdAndQuiz_Id(Long userId, Long quizId);

    // Find quiz result by user ID and quiz ID
    Optional<UserQuizResult> findByUser_IdAndQuiz_Id(Long userId, Long quizId);

    // Custom method to filter quiz results by userId, quizId, startDate, and endDate
    @Query("SELECT uqr FROM UserQuizResult uqr WHERE "
            + "(:userId IS NULL OR uqr.user.id = :userId) AND "
            + "(:quizId IS NULL OR uqr.quiz.id = :quizId) AND "
            + "(:startDate IS NULL OR uqr.submissionDate >= :startDate) AND "
            + "(:endDate IS NULL OR uqr.submissionDate <= :endDate)")
    List<UserQuizResult> findByFilters(
            @Param("userId") Long userId,
            @Param("quizId") Long quizId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );
}
