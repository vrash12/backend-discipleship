package org.example.serve.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "answerenglish")
public class AnswerEnglish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerID;

    @Column(name = "answer_text")
    private String answerText;

    @Column(name = "questionID", insertable = false, updatable = false)
    private Long questionID;

    @Column(name = "is_correct")
    private boolean isCorrect;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "questionID")
    @JsonBackReference("questionEnglish-answers")
    private QuestionEnglish question;

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

    public Long getQuestionID() {
        return questionID;
    }

    public void setQuestionID(Long questionID) {
        this.questionID = questionID;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public QuestionEnglish getQuestion() {
        return question;
    }

    public void setQuestion(QuestionEnglish question) {
        this.question = question;
    }
}
