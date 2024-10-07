package org.example.serve.controllers;

import org.example.serve.model.UserQuizResult;
import org.example.serve.service.UserQuizResultsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-quiz-results")
public class UserQuizResultsController {

    @Autowired
    private UserQuizResultsService userQuizResultsService;

    // Get quiz results by user ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserQuizResult>> getResultsByUserId(@PathVariable Long userId) {
        List<UserQuizResult> results = userQuizResultsService.getResultsByUserId(userId);
        if (results.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(results);
    }

    // Get quiz results by quiz ID
    @GetMapping("/quiz/{quizId}")
    public ResponseEntity<List<UserQuizResult>> getResultsByQuizId(@PathVariable Long quizId) {
        List<UserQuizResult> results = userQuizResultsService.getResultsByQuizId(quizId);
        if (results.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(results);
    }

    // Save or submit a new quiz result
    @PostMapping("/submit")
    public ResponseEntity<UserQuizResult> submitQuizResult(@RequestBody UserQuizResult userQuizResult) {
        try {
            Long userId = userQuizResult.getUser().getId();
            Long quizId = userQuizResult.getQuiz().getQuizId(); // Adjust as necessary
            int score = userQuizResult.getScore();
            int totalQuestions = userQuizResult.getTotalQuestions();

            UserQuizResult savedResult = userQuizResultsService.saveQuizResult(userId, quizId, score, totalQuestions);
            return ResponseEntity.ok(savedResult);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exception appropriately
            return ResponseEntity.status(500).build();
        }
    }
}
