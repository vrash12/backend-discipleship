package org.example.serve.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommentaryScrapingService {

    private static final Map<String, String> BOOK_MAP = new HashMap<>();

    static {
        BOOK_MAP.put("gn", "genesis");
        BOOK_MAP.put("ex", "exodus");
        BOOK_MAP.put("lv", "leviticus");
        BOOK_MAP.put("nm", "numbers");
        BOOK_MAP.put("dt", "deuteronomy");
        BOOK_MAP.put("js", "joshua");
        BOOK_MAP.put("jdg", "judges");
        BOOK_MAP.put("ru", "ruth");
        BOOK_MAP.put("1sm", "1-samuel");
        BOOK_MAP.put("2sm", "2-samuel");
        BOOK_MAP.put("1kg", "1-kings");
        BOOK_MAP.put("2kg", "2-kings");
        BOOK_MAP.put("1ch", "1-chronicles");
        BOOK_MAP.put("2ch", "2-chronicles");
        BOOK_MAP.put("ezr", "ezra");
        BOOK_MAP.put("neh", "nehemiah");
        BOOK_MAP.put("est", "esther");
        BOOK_MAP.put("job", "job");
        BOOK_MAP.put("ps", "psalms");
        BOOK_MAP.put("prv", "proverbs");
        BOOK_MAP.put("ecc", "ecclesiastes");
        BOOK_MAP.put("ss", "song-of-solomon");
        BOOK_MAP.put("isa", "isaiah");
        BOOK_MAP.put("jer", "jeremiah");
        BOOK_MAP.put("lam", "lamentations");
        BOOK_MAP.put("ezk", "ezekiel");
        BOOK_MAP.put("dn", "daniel");
        BOOK_MAP.put("hos", "hosea");
        BOOK_MAP.put("joel", "joel");
        BOOK_MAP.put("amos", "amos");
        BOOK_MAP.put("obad", "obadiah");
        BOOK_MAP.put("jon", "jonah");
        BOOK_MAP.put("mic", "micah");
        BOOK_MAP.put("nah", "nahum");
        BOOK_MAP.put("hab", "habakkuk");
        BOOK_MAP.put("zep", "zephaniah");
        BOOK_MAP.put("hag", "haggai");
        BOOK_MAP.put("zec", "zechariah");
        BOOK_MAP.put("mal", "malachi");
        BOOK_MAP.put("mt", "matthew");
        BOOK_MAP.put("mk", "mark");
        BOOK_MAP.put("lk", "luke");
        BOOK_MAP.put("jn", "john");
        BOOK_MAP.put("acts", "acts");
        BOOK_MAP.put("rom", "romans");
        BOOK_MAP.put("1cor", "1-corinthians");
        BOOK_MAP.put("2cor", "2-corinthians");
        BOOK_MAP.put("gal", "galatians");
        BOOK_MAP.put("eph", "ephesians");
        BOOK_MAP.put("php", "philippians");
        BOOK_MAP.put("col", "colossians");
        BOOK_MAP.put("1thes", "1-thessalonians");
        BOOK_MAP.put("2thes", "2-thessalonians");
        BOOK_MAP.put("1tim", "1-timothy");
        BOOK_MAP.put("2tim", "2-timothy");
        BOOK_MAP.put("tit", "titus");
        BOOK_MAP.put("phlm", "philemon");
        BOOK_MAP.put("heb", "hebrews");
        BOOK_MAP.put("jas", "james");
        BOOK_MAP.put("1pet", "1-peter");
        BOOK_MAP.put("2pet", "2-peter");
        BOOK_MAP.put("1jn", "1-john");
        BOOK_MAP.put("2jn", "2-john");
        BOOK_MAP.put("3jn", "3-john");
        BOOK_MAP.put("jud", "jude");
        BOOK_MAP.put("rev", "revelation");
    }

    public List<String> getCommentary(String book, int chapter) throws IOException {
        String bookName = BOOK_MAP.get(book.toLowerCase());
        if (bookName == null) {
            throw new IllegalArgumentException("Invalid book abbreviation");
        }

        String url = "https://enduringword.com/bible-commentary/" + bookName + "-" + chapter + "/";
        Document doc = Jsoup.connect(url).get();

        List<String> commentary = new ArrayList<>();

        // The div with class 'avia_textblock' contains the main content
        Elements commentarySections = doc.select("div.avia_textblock");

        for (Element section : commentarySections) {
            // Extract paragraphs and split by verse markers
            Elements paragraphs = section.select("p");
            for (Element paragraph : paragraphs) {
                // Split paragraph text by verse numbers (using regex to identify verse numbers)
                String[] verseParts = paragraph.html().split("(?<=</sup>)\\s*"); // Splits after each verse number
                for (String versePart : verseParts) {
                    String cleanedVerse = Jsoup.parse(versePart).text(); // Clean HTML tags
                    if (!cleanedVerse.trim().isEmpty()) {
                        commentary.add(cleanedVerse);
                    }
                }
            }
        }

        return commentary;
    }
}
