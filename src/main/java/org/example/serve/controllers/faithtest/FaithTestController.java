package org.example.serve.controllers.faithtest;

import org.example.serve.service.quiz.FaithTestService;
import org.example.serve.dto.FaithTestResponseDTO;
import org.example.serve.dto.QuestionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.example.serve.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@RestController
@RequestMapping("/api/faith-test")
public class FaithTestController {

    private static final Logger logger = LoggerFactory.getLogger(FaithTestController.class);

    @Autowired
    private FaithTestService faithTestService;

    // Fetch questions based on the language
    @GetMapping("/questions")
    public ResponseEntity<List<QuestionDTO>> getQuestionsByLanguage(@RequestParam String language) {
        List<QuestionDTO> questions = faithTestService.getQuestionsByLanguage(language);
        return ResponseEntity.ok(questions);
    }

    // Endpoint to save user's answers
    @PostMapping("/{userId}/response")
    public ResponseEntity<String> saveUserResponse(@PathVariable Long userId,
                                                   @RequestParam Long testId,
                                                   @RequestParam String selectedAnswer,
                                                   @RequestParam String language) {
        try {
            faithTestService.saveUserResponse(userId, testId, selectedAnswer, language);
            return ResponseEntity.ok("User response saved successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error saving user response: " + e.getMessage());
        }
    }

    // Check if the user has completed the test
    @GetMapping("/{userId}/faith-test-status")
    public ResponseEntity<String> checkTestStatus(@PathVariable Long userId) {
        boolean hasCompleted = faithTestService.hasUserCompletedTest(userId);
        if (hasCompleted) {
            return ResponseEntity.ok("User has completed the faith test.");
        } else {
            return ResponseEntity.ok("User has not completed the faith test.");
        }
    }

    // Mark test as completed
    @PutMapping("/completed-faith-test/{userId}")
    public ResponseEntity<String> markTestAsCompleted(@PathVariable Long userId) {
        try {
            faithTestService.markTestAsCompleted(userId);
            return ResponseEntity.ok("Test marked as completed.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error marking test as completed: " + e.getMessage());
        }
    }

    // Add this method to your `FaithTestController`
    @GetMapping("/all-users")
    public ResponseEntity<List<User>> getAllUsersWithCompletedFaithTest() {
        try {
            List<User> users = faithTestService.getAllUsersWithCompletedFaithTest();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/{userId}/responses")
    public ResponseEntity<List<FaithTestResponseDTO>> getUserResponses(@PathVariable Long userId) {
        try {
            logger.info("Fetching responses for userId: {}", userId);
            List<FaithTestResponseDTO> responses = faithTestService.getUserFaithTestResponses(userId);
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            logger.error("Error fetching responses for userId: ", e);
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/response/{responseId}")
    public ResponseEntity<FaithTestResponseDTO> getFaithTestResponseById(@PathVariable Long responseId) {
        try {
            FaithTestResponseDTO responseDTO = faithTestService.getFaithTestResponseById(responseId);
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }



}
