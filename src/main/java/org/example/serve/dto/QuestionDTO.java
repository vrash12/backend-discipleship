package org.example.serve.dto;

import java.util.List;

public class QuestionDTO {

    private String questionText;
    private List<String> answerOptions;
    // dto.setQuizTitle(result.getQuiz().getTitle());
    private String quizTitle;

    // Getters and setters
    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public List<String> getAnswerOptions() {
        return answerOptions;
    }

    public void setAnswerOptions(List<String> answerOptions) {
        this.answerOptions = answerOptions;
    }

    public String getQuizTitle() {
        return quizTitle;
    }

    public void setQuizTitle(String quizTitle) {
        this.quizTitle = quizTitle;
    }
}