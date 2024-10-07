package org.example.serve.model; // Adjust the package name based on your project structure

public class BibleVerse {
    private String book;  // The book of the Bible (e.g., "John", "Psalm")
    private int chapter;  // The chapter number
    private int verse;    // The verse number
    private String version;  // The Bible version/translation (e.g., "NIV", "KJV")

    // Constructor without version (default version)
    public BibleVerse(String book, int chapter, int verse) {
        this.book = book;
        this.chapter = chapter;
        this.verse = verse;
        this.version = "NIV";  // Default version if none provided
    }

    // Constructor with version
    public BibleVerse(String book, int chapter, int verse, String version) {
        this.book = book;
        this.chapter = chapter;
        this.verse = verse;
        this.version = version;
    }

    // Getters and Setters
    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public int getChapter() {
        return chapter;
    }

    public void setChapter(int chapter) {
        this.chapter = chapter;
    }

    public int getVerse() {
        return verse;
    }

    public void setVerse(int verse) {
        this.verse = verse;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return book + " " + chapter + ":" + verse + " (" + version + ")";
    }
}
