package org.example.serve.controllers.admin;

import org.example.serve.service.UserQuizResultsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;



@RestController
@RequestMapping("/api/admin")
public class AdminQuizController {

    @Autowired
    private UserQuizResultsService userQuizResultsService;


    @PostMapping("/reset-quiz")
    public ResponseEntity<?> resetQuizForUser(@RequestBody Map<String, Long> requestData) {
        Long userId = requestData.get("userId");
        Long quizId = requestData.get("quizId");

        try {
            // Reset the quiz result
            userQuizResultsService.resetQuizResult(userId, quizId);
            return ResponseEntity.ok().body(Collections.singletonMap("success", true));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Collections.singletonMap("success", false));
        }
    }
}
