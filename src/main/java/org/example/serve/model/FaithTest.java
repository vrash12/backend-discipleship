package org.example.serve.model;
import jakarta.persistence.*;

@Entity
@Table(name = "faith_test")
public class FaithTest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long testId;

    @Column(name = "question_text_tagalog", nullable = false)
    private String questionTextTagalog;

    @Column(name = "question_text_english", nullable = false)
    private String questionTextEnglish;

    @Column(name = "answer_options_tagalog", columnDefinition = "json", nullable = false)
    private String answerOptionsTagalog;

    @Column(name = "answer_options_english", columnDefinition = "json", nullable = false)
    private String answerOptionsEnglish;

    // Getters and Setters

    public Long getTestId() {
        return testId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
    }

    public String getQuestionTextTagalog() {
        return questionTextTagalog;
    }

    public void setQuestionTextTagalog(String questionTextTagalog) {
        this.questionTextTagalog = questionTextTagalog;
    }

    public String getQuestionTextEnglish() {
        return questionTextEnglish;
    }

    public void setQuestionTextEnglish(String questionTextEnglish) {
        this.questionTextEnglish = questionTextEnglish;
    }

    public String getAnswerOptionsTagalog() {
        return answerOptionsTagalog;
    }

    public void setAnswerOptionsTagalog(String answerOptionsTagalog) {
        this.answerOptionsTagalog = answerOptionsTagalog;
    }

    public String getAnswerOptionsEnglish() {
        return answerOptionsEnglish;
    }

    public void setAnswerOptionsEnglish(String answerOptionsEnglish) {
        this.answerOptionsEnglish = answerOptionsEnglish;
    }
}
