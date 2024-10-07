package org.example.serve.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "quiz")
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quizid")
    private Long id;  // Renamed property

    private String title;
    private String description;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("quiz-englishQuestions")
    private List<QuestionEnglish> englishQuestions;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("quiz-tagalogQuestions")
    private List<QuestionTagalog> tagalogQuestions;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lessonId", nullable = false)
    private Lesson lesson;

    // Getters and Setters

    public Long getQuizId() {  // Updated getter
        return id;
    }

    public void setQuizId(Long id) {  // Updated setter
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public List<QuestionEnglish> getEnglishQuestions() {
        return englishQuestions;
    }

    public void setEnglishQuestions(List<QuestionEnglish> englishQuestions) {
        this.englishQuestions = englishQuestions;
    }

    public List<QuestionTagalog> getTagalogQuestions() {
        return tagalogQuestions;
    }

    public void setTagalogQuestions(List<QuestionTagalog> tagalogQuestions) {
        this.tagalogQuestions = tagalogQuestions;
    }

    public Lesson getLesson(){
        return lesson;
    }

    public void setLesson(Lesson lesson){
        this.lesson = lesson;
    }
}
