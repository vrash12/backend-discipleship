package org.example.serve.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "answertagalog")
public class AnswerTagalog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerID;

    @Column(name = "answer_text")
    private String answerText;

    @Column(name = "is_correct")
    private boolean isCorrect;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "questionID", nullable = false)
    @JsonBackReference
    private QuestionTagalog questionTagalog;

    // Getters and Setters

    public Long getAnswerID() {
        return answerID;
    }

    public void setAnswerID(Long answerID) {
        this.answerID = answerID;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public QuestionTagalog getQuestionTagalog() {
        return questionTagalog;
    }

    public void setQuestionTagalog(QuestionTagalog questionTagalog) {
        this.questionTagalog = questionTagalog;
    }
}
