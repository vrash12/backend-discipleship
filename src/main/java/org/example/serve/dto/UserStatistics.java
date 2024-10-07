package org.example.serve.dto;
import org.example.serve.model.User;
import org.example.serve.model.UserQuizResult;
import java.util.ArrayList;
import java.util.List;


public class UserStatistics {
    private Long userId;
    private String userName;
    private List<QuizResultDto> quizResults;
    private double averageScore;
    private int highestScore;
    private int lowestScore;
    private String quizTitle;

    // Constructor and methods
    public UserStatistics(User user) {
        this.userId = user.getId();
        this.userName = user.getName();
        this.quizResults = new ArrayList<>();
        this.highestScore = Integer.MIN_VALUE;
        this.lowestScore = Integer.MAX_VALUE;
    }

    public void addResult(UserQuizResult result) {
        QuizResultDto dto = new QuizResultDto();
        dto.setQuizId(result.getQuiz().getQuizId());
        dto.setQuizTitle(result.getQuiz().getTitle()); // This line requires setQuizTitle()
        dto.setScore(result.getScore());
        dto.setTotalQuestions(result.getTotalQuestions());
        dto.setSubmissionDate(result.getSubmissionDate()); // This line requires setSubmissionDate()
        quizResults.add(dto);

        // Update stats
        int score = result.getScore();
        averageScore = ((averageScore * (quizResults.size() - 1)) + score) / quizResults.size();
        highestScore = Math.max(highestScore, score);
        lowestScore = Math.min(lowestScore, score);
    }

    // Getters and setters

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<QuizResultDto> getQuizResults() {
        return quizResults;
    }

    public void setQuizResults(List<QuizResultDto> quizResults) {
        this.quizResults = quizResults;
    }

    public double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(double averageScore) {
        this.averageScore = averageScore;
    }

    public int getHighestScore() {
        return highestScore;
    }

    public void setHighestScore(int highestScore) {
        this.highestScore = highestScore;
    }

    public int getLowestScore() {
        return lowestScore;
    }

    public void setLowestScore(int lowestScore) {
        this.lowestScore = lowestScore;
    }

    public void setQuizTitle(String quizTitle) {
        this.quizTitle = quizTitle;
    }

    public String getQuizTitle() {
        return quizTitle;
    }
}
