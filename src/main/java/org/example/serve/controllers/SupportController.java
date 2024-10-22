// SupportController.java

package org.example.serve.controllers;

import org.example.serve.model.Feedback;
import org.example.serve.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Allow requests from your frontend domain or use @CrossOrigin("*") for all origins
@CrossOrigin(origins = "http://localhost:19006")
@RestController
@RequestMapping("/api/support")
public class SupportController {

    private final FeedbackService feedbackService;

    @Autowired
    public SupportController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PostMapping("/feedback")
    public ResponseEntity<String> submitFeedback(@RequestBody Feedback feedback) {
        try {
            feedbackService.submitFeedback(feedback);
            return ResponseEntity.ok("Feedback submitted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to submit feedback.");
        }
    }
}
