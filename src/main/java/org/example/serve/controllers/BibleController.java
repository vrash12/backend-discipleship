package org.example.serve.controllers;

import org.example.serve.service.BibleScrapingService;
import org.example.serve.util.BibleBookMapping;
import org.example.serve.model.BibleVerse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/bible")
public class BibleController {

    private final BibleScrapingService bibleScrapingService;
    private final List<BibleVerse> versesToScrape;         // Original list of verses
    private final List<BibleVerse> encouragingVerses;      // List of encouraging verses

    @Autowired
    public BibleController(BibleScrapingService bibleScrapingService) {
        this.bibleScrapingService = bibleScrapingService;
        this.versesToScrape = initializeVerses();
        this.encouragingVerses = initializeEncouragingVerses();
    }

    private List<BibleVerse> initializeVerses() {
        List<BibleVerse> verses = new ArrayList<>();
        // Add your predefined verses to the list
        verses.add(new BibleVerse("John", 3, 16));
        verses.add(new BibleVerse("Psalm", 23, 1));
        // ... (other verses)
        return verses;
    }

    private List<BibleVerse> initializeEncouragingVerses() {
        List<BibleVerse> verses = new ArrayList<>();
        // Add encouraging verses to the list
        verses.add(new BibleVerse("Jeremiah", 29, 11));       // "For I know the plans I have for you..."
        verses.add(new BibleVerse("Isaiah", 41, 10));         // "Do not fear, for I am with you..."
        verses.add(new BibleVerse("Philippians", 4, 13));     // "I can do all things through Christ..."
        verses.add(new BibleVerse("Psalm", 23, 4));           // "Even though I walk through the valley..."
        verses.add(new BibleVerse("Joshua", 1, 9));           // "Be strong and courageous..."
        verses.add(new BibleVerse("Psalm", 46, 1));           // "God is our refuge and strength..."
        verses.add(new BibleVerse("Romans", 8, 28));          // "And we know that in all things God works..."
        verses.add(new BibleVerse("Matthew", 11, 28));        // "Come to me, all you who are weary..."
        verses.add(new BibleVerse("Psalm", 34, 18));          // "The Lord is close to the brokenhearted..."
        verses.add(new BibleVerse("2 Corinthians", 12, 9));   // "My grace is sufficient for you..."
        // Add more encouraging verses as needed
        return verses;
    }

    // Endpoint for fetching a random encouraging verse of the day
    @GetMapping("/votd")
    public ResponseEntity<String> getVerseOfTheDay() {
        try {
            // Select a random verse from the predefined encouraging verses list
            Random random = new Random();
            BibleVerse randomVerse = encouragingVerses.get(random.nextInt(encouragingVerses.size()));

            // Scrape and fetch all verses in the chapter
            List<String> verseContentList = bibleScrapingService.scrapeAndCleanVerses(
                    randomVerse.getBook(),
                    randomVerse.getChapter(),
                    randomVerse.getVersion() // Use the version from the BibleVerse object
            );

            // Get the specific verse by its number
            int verseNumber = randomVerse.getVerse();
            String verseContent = "";
            if (verseNumber - 1 < verseContentList.size()) {
                verseContent = verseContentList.get(verseNumber - 1);

                // Include the reference
                String reference = randomVerse.getBook() + " " + randomVerse.getChapter() + ":" + randomVerse.getVerse();
                String result = "\"" + verseContent + "\"\n\n- " + reference;

                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Verse not found.");
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching verse of the day.");
        }
    }

    // Other endpoints remain unchanged

    @GetMapping("/multiple-verses")
    public ResponseEntity<List<String>> getMultipleVerses() {
        try {
            List<String> verses = new ArrayList<>();
            for (BibleVerse verse : versesToScrape) {
                // Scrape and fetch all verses in the chapter
                List<String> verseContentList = bibleScrapingService.scrapeAndCleanVerses(
                        verse.getBook(),
                        verse.getChapter(),
                        verse.getVersion()
                );

                int verseNumber = verse.getVerse();
                if (verseNumber - 1 < verseContentList.size()) {
                    verses.add(verseContentList.get(verseNumber - 1));
                } else {
                    verses.add("Verse not found.");
                }
            }
            return ResponseEntity.ok(verses);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/verses")
    public ResponseEntity<String> getVerses(
            @RequestParam String book,
            @RequestParam int chapter,
            @RequestParam(defaultValue = "NIV") String version
    ) {
        try {
            // Translate abbreviation to Tagalog name if needed
            String tagalogBookName = BibleBookMapping.getTagalogBookName(book, version);

            // Fetch all verses in the chapter
            List<String> versesContent = bibleScrapingService.scrapeAndCleanVerses(
                    tagalogBookName,
                    chapter,
                    version
            );

            // Remove the first element if it's a title (does not start with a verse number)
            if (!versesContent.isEmpty() && !versesContent.get(0).matches("^\\d+\\s.*")) {
                versesContent.remove(0);
            }

            // Join the verses into a single string
            String versesString = String.join("\n", versesContent);

            return ResponseEntity.ok(versesString);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching verses.");
        }
    }

}
