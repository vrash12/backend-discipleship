package org.example.serve.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;
import java.util.List;

@Entity
@Table(name = "questionenglish")
public class QuestionEnglish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "questionID")
    private Long questionID;

    @Column(name = "question_text")
    private String questionText;

    @Column(name = "correct_answer")
    private String correctAnswer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quizID", nullable = false)
    @JsonBackReference("quiz-englishQuestions")
    private Quiz quiz;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("questionEnglish-answers")
    private List<AnswerEnglish> answers;

    // Getters and Setters

    public Long getQuestionID() {
        return questionID;
    }

    public void setQuestionID(Long questionID){
        this.questionID = questionID;
    }

    public String getQuestionText(){
        return questionText;
    }

    public void setQuestionText(String questionText){
        this.questionText = questionText;
    }

    public String getCorrectAnswer(){
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer){
        this.correctAnswer = correctAnswer;
    }

    public Quiz getQuiz(){
        return quiz;
    }

    public void setQuiz(Quiz quiz){
        this.quiz = quiz;
    }

    public List<AnswerEnglish> getAnswers(){
        return answers;
    }

    public void setAnswers(List<AnswerEnglish> answers){
        this.answers = answers;
    }
}
