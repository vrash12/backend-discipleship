package org.example.serve.model;

import jakarta.persistence.*;

@Entity
@Table(name = "faith_test_responses")
public class FaithTestResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long responseId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "test_id", nullable = false)
    private FaithTest faithTest;

    @Column(name = "selected_answer", nullable = false)
    private String selectedAnswer;

    // Getters and Setters

    public Long getResponseId() {
        return responseId;
    }

    public void setResponseId(Long responseId) {
        this.responseId = responseId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public FaithTest getFaithTest() {
        return faithTest;
    }

    public void setFaithTest(FaithTest faithTest) {
        this.faithTest = faithTest;
    }

    public String getSelectedAnswer() {
        return selectedAnswer;
    }

    public void setSelectedAnswer(String selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }

    //setSelectedAnswerEnglish
    public void setSelectedAnswerEnglish(String selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }

    //setSelectedAnswerTagalog
    public void setSelectedAnswerTagalog(String selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }
}
