package org.example.serve.controllers;

import jakarta.transaction.Transactional;
import org.example.serve.model.*;
import org.example.serve.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import org.example.serve.service.UserQuizResultsService;
import org.example.serve.service.QuizService;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @Autowired
    private UserQuizResultsService userQuizResultsService;


    @Transactional
    @GetMapping("/english/{quizId}")
    public ResponseEntity<Map<String, Object>> getEnglishQuizById(@PathVariable Long quizId) {
        Optional<Quiz> quizOptional = quizService.getQuizById(quizId);
        if (quizOptional.isPresent()) {
            Quiz quiz = quizOptional.get();

            List<QuestionEnglish> englishQuestions = quizService.getEnglishQuestionsByQuizId(quizId);
            System.out.println("Number of English Questions: " + (englishQuestions != null ? englishQuestions.size() : 0));

            // Prepare response data
            Map<String, Object> response = new HashMap<>();
            response.put("quizId", quiz.getQuizId());
            response.put("title", quiz.getTitle());
            response.put("description", quiz.getDescription());
            response.put("totalQuestions", englishQuestions.size());

            List<Map<String, Object>> questions = new ArrayList<>();
            for (QuestionEnglish question : englishQuestions) {
                Map<String, Object> questionData = new HashMap<>();
                questionData.put("questionID", question.getQuestionID());
                questionData.put("questionText", question.getQuestionText());
                questionData.put("correctAnswer", question.getCorrectAnswer());

                List<Map<String, Object>> answers = new ArrayList<>();
                for (AnswerEnglish answer : question.getAnswers()) {
                    Map<String, Object> answerData = new HashMap<>();
                    answerData.put("answerID", answer.getAnswerID());
                    answerData.put("answerText", answer.getAnswerText());
                    answerData.put("correct", answer.isCorrect());
                    answers.add(answerData);
                }

                questionData.put("answers", answers);
                questions.add(questionData);
            }

            response.put("questions", questions);

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    @Transactional
    @GetMapping("/tagalog/{quizId}")
    public ResponseEntity<Map<String, Object>> getTagalogQuizById(@PathVariable Long quizId) {
        System.out.println("Fetching Tagalog quiz with ID: " + quizId);

        Optional<Quiz> quizOptional = quizService.getQuizById(quizId);
        if (quizOptional.isPresent()) {
            Quiz quiz = quizOptional.get();

            List<QuestionTagalog> tagalogQuestions = quizService.getTagalogQuestionsByQuizId(quizId);
            System.out.println("Number of Tagalog Questions: " + (tagalogQuestions != null ? tagalogQuestions.size() : 0));

            // For each question, fetch the corresponding answers
            for (QuestionTagalog question : tagalogQuestions) {
                List<AnswerTagalog> tagalogAnswers = quizService.getTagalogAnswersByQuestionId(question.getQuestionID());
                question.setAnswers(tagalogAnswers);
            }

            // Prepare response data
            Map<String, Object> response = new HashMap<>();
            response.put("quizId", quiz.getQuizId());
            response.put("title", quiz.getTitle());
            response.put("description", quiz.getDescription());
            response.put("totalQuestions", tagalogQuestions.size());

            List<Map<String, Object>> questions = new ArrayList<>();
            for (QuestionTagalog question : tagalogQuestions) {
                Map<String, Object> questionData = new HashMap<>();
                questionData.put("questionID", question.getQuestionID());
                questionData.put("questionText", question.getQuestionText());
                // If you have a correctAnswer field
                // questionData.put("correctAnswer", question.getCorrectAnswer());

                List<Map<String, Object>> answers = new ArrayList<>();
                for (AnswerTagalog answer : question.getAnswers()) {
                    Map<String, Object> answerData = new HashMap<>();
                    answerData.put("answerID", answer.getAnswerID());
                    answerData.put("answerText", answer.getAnswerText());
                    answerData.put("isCorrect", answer.isCorrect()); // Use 'isCorrect' or 'correct' as per your frontend expectations
                    answers.add(answerData);
                }

                questionData.put("answers", answers);
                questions.add(questionData);
            }

            response.put("questions", questions);

            return ResponseEntity.ok(response);
        } else {
            System.out.println("Tagalog quiz not found for ID: " + quizId);
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/submit")
    public ResponseEntity<Map<String, Object>> submitQuizResult(@RequestBody Map<String, Object> quizResultData) {
        try {
            // Extract data from request body
            Long userId = Long.valueOf(quizResultData.get("userId").toString());
            Long quizId = Long.valueOf(quizResultData.get("quizId").toString());
            int score = Integer.parseInt(quizResultData.get("score").toString());
            int totalQuestions = Integer.parseInt(quizResultData.get("totalQuestions").toString());

            // Check if the user has already submitted this quiz
            boolean alreadySubmitted = userQuizResultsService.hasUserSubmittedQuiz(userId, quizId);
            if (alreadySubmitted) {
                Map<String, Object> response = new HashMap<>();
                response.put("message", "You have already submitted this quiz.");
                return ResponseEntity.badRequest().body(response);
            }

            // Save the quiz result
            userQuizResultsService.saveQuizResult(userId, quizId, score, totalQuestions);

            // Prepare a JSON response
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Quiz result saved successfully.");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();

            Map<String, Object> response = new HashMap<>();
            response.put("message", "An error occurred while saving the quiz result.");
            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping("/status/{quizId}")
    public ResponseEntity<?> checkQuizStatus(@PathVariable Long quizId, @RequestParam Long userId) {
        boolean alreadySubmitted = userQuizResultsService.hasUserSubmittedQuiz(userId, quizId);
        Map<String, Object> response = new HashMap<>();
        response.put("alreadySubmitted", alreadySubmitted);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/completed")
    public ResponseEntity<?> getCompletedQuizzes(@RequestParam Long userId) {
        List<Long> completedQuizIds = userQuizResultsService.getCompletedQuizIdsByUserId(userId);
        Map<String, Object> response = new HashMap<>();
        response.put("completedQuizIds", completedQuizIds);
        return ResponseEntity.ok(response);
    }


    // Other methods...
}
