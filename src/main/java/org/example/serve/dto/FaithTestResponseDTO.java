package org.example.serve.dto;


public class FaithTestResponseDTO {
    private Long userId;
    private Long testId;
    private String selectedAnswerTagalog;
    private String selectedAnswerEnglish;

    // Getters and Setters

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTestId() {
        return testId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
    }

    public String getSelectedAnswerTagalog() {
        return selectedAnswerTagalog;
    }

    public void setSelectedAnswerTagalog(String selectedAnswerTagalog) {
        this.selectedAnswerTagalog = selectedAnswerTagalog;
    }

    public String getSelectedAnswerEnglish() {
        return selectedAnswerEnglish;
    }

    public void setSelectedAnswerEnglish(String selectedAnswerEnglish) {
        this.selectedAnswerEnglish = selectedAnswerEnglish;
    }


}
