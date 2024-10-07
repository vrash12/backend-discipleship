package org.example.serve.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class BibleScrapingService {

    public List<String> scrapeAndCleanVerses(String book, int chapter, String version) throws IOException {
        String url = "https://www.biblegateway.com/passage/?search=" +
                book + "%20" + chapter + "&version=" + version;
        Document doc = Jsoup.connect(url).get();

        List<String> versesList = new ArrayList<>();

        // Select the container that holds the verses
        Element passageContent = doc.selectFirst("div.passage-content");

        if (passageContent != null) {
            // Select all verse elements
            Elements verseElements = passageContent.select("span[class~=(^|\\s)text(\\s|$)]");

            for (Element verseElement : verseElements) {
                // Extract the verse number
                Elements verseNumElements = verseElement.select("sup.versenum");

                String verseNumber = "";
                if (!verseNumElements.isEmpty()) {
                    verseNumber = verseNumElements.first().text();
                }

                // Remove the verse number from the verse text if it's included
                String verseText = verseElement.text().replaceFirst("^\\d+\\s*", "").trim();

                // Clean the text
                String cleanedVerseText = cleanText(verseText);

                if (!cleanedVerseText.isEmpty()) {
                    if (!verseNumber.isEmpty()) {
                        versesList.add(verseNumber + " " + cleanedVerseText);
                    } else {
                        versesList.add(cleanedVerseText);
                    }
                }
            }
        } else {
            versesList.add("Error: Unable to find passage content.");
        }

        return versesList;
    }

    private String cleanText(String text) {
        // Remove cross-reference markers and footnotes
        text = text.replaceAll("\\(\\s?[A-Za-z]\\s?\\)", "").trim();
        text = text.replaceAll("\\[\\s?[a-z]\\s?\\]", "").trim();

        return text;
    }
}
