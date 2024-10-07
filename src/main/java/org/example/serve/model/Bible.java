package org.example.serve.model;
import jakarta.persistence.*;

@Entity
public class Bible {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bibleID;

    private String book;
    private String chapter;
    private String verse;

    @Lob
    private String content;

    // Getters and Setters
    public Long getBibleID() {
        return bibleID;
    }

    public void setBibleID(Long bibleID) {
        this.bibleID = bibleID;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public String getVerse() {
        return verse;
    }

    public void setVerse(String verse) {
        this.verse = verse;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}