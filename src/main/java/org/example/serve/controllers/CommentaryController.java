package org.example.serve.controllers;

import org.example.serve.service.CommentaryScrapingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/commentary")
public class CommentaryController {

    @Autowired
    private CommentaryScrapingService scrapingService;

    @GetMapping
    public Map<String, Object> getCommentary(@RequestParam String book, @RequestParam int chapter) {
        try {
            List<String> commentary = scrapingService.getCommentary(book, chapter);
            return Map.of(
                    "book", book,
                    "chapter", chapter,
                    "commentary", commentary
            );
        } catch (IllegalArgumentException e) {
            return Map.of("error", "Invalid book abbreviation: " + book);
        } catch (IOException e) {
            return Map.of("error", "Failed to scrape commentary: " + e.getMessage());
        }
    }
}
