package org.example.serve.controllers.admin;

import org.example.serve.model.UserQuizResult;
import org.example.serve.repositories.*;
import org.example.serve.dto.UserStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.*;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.example.serve.repositories.quiz.UserQuizResultRepository;
import org.example.serve.repositories.quiz.QuizRepository;



@RestController
@RequestMapping("/api/admin/quiz-statistics")
public class AdminQuizStatisticsController {

    @Autowired
    private UserQuizResultRepository userQuizResultRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuizRepository quizRepository;

    @GetMapping
    public ResponseEntity<?> getQuizStatistics(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Long quizId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        List<UserQuizResult> results = userQuizResultRepository.findByFilters(userId, quizId, startDate, endDate);

        // Aggregate data per user
        Map<Long, UserStatistics> userStatsMap = new HashMap<>();

        for (UserQuizResult result : results) {
            Long uid = result.getUser().getId();
            UserStatistics stats = userStatsMap.getOrDefault(uid, new UserStatistics(result.getUser()));
            stats.addResult(result);
            userStatsMap.put(uid, stats);
        }

        List<UserStatistics> userStatsList = new ArrayList<>(userStatsMap.values());

        // Implement pagination manually if needed
        int start = Math.min(page * size, userStatsList.size());
        int end = Math.min(start + size, userStatsList.size());
        List<UserStatistics> paginatedList = userStatsList.subList(start, end);

        return ResponseEntity.ok(paginatedList);
    }
}
