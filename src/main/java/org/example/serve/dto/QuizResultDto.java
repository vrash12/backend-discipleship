package org.example.serve.dto;

import java.time.LocalDateTime;
import java.util.Map;

public class QuizResultDto {
    private Long quizId;
    private String quizTitle; // Add this field
    private Long userId;
    private int score;
    private int totalQuestions;
    private LocalDateTime submissionDate; // Add this field
    private Map<Long, Long> selectedAnswers; // Map of questionID to selected answerID

    // Getters and Setters

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    public String getQuizTitle() {
        return quizTitle;
    }

    public void setQuizTitle(String quizTitle) { // Add this method
        this.quizTitle = quizTitle;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public Map<Long, Long> getSelectedAnswers() {
        return selectedAnswers;
    }

    public void setSelectedAnswers(Map<Long, Long> selectedAnswers) {
        this.selectedAnswers = selectedAnswers;
    }

    public LocalDateTime getSubmissionDate() { // Add this method
        return submissionDate;
    }

    public void setSubmissionDate(LocalDateTime submissionDate) { // Add this method
        this.submissionDate = submissionDate;
    }
}
