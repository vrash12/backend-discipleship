// FeedbackService.java

package org.example.serve.service;

import org.example.serve.model.Feedback;
import org.example.serve.repositories.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    // private final EmailService emailService; // If you have an email service

    @Autowired
    public FeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
        // this.emailService = emailService;
    }

    public void submitFeedback(Feedback feedback) {
        // Save feedback to the database
        feedbackRepository.save(feedback);

        // Optionally, send an email notification to the admin
        // emailService.sendFeedbackNotification(feedback);
    }
}
