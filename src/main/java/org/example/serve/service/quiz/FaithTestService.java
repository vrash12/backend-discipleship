package org.example.serve.service.quiz;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.serve.model.FaithTest;
import org.example.serve.model.FaithTestResponse;
import org.example.serve.model.User;
import org.example.serve.model.UserFaithTestStatus;
import org.example.serve.dto.QuestionDTO;
import org.example.serve.repositories.faithtest.FaithTestRepository;
import org.example.serve.repositories.faithtest.FaithTestResponseRepository;
import org.example.serve.repositories.faithtest.FaithTestCompletionRepository;
import org.example.serve.repositories.UserRepository;
import org.example.serve.repositories.faithtest.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import faithtest.FaithTestResponseDTO;
import org.example.serve.dto.UserFaithTestStatusDTO;
import org.example.serve.dto.FaithTestResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class FaithTestService {

    private static final Logger logger = LoggerFactory.getLogger(FaithTestService.class);

    @Autowired
    private FaithTestRepository faithTestRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FaithTestResponseRepository faithTestResponseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserFaithTestStatusRepository userFaithTestStatusRepository;


    // Method to get questions based on the selected language
    public List<QuestionDTO> getQuestionsByLanguage(String language) {
        List<FaithTest> faithTests = faithTestRepository.findAll(); // Fetch all questions
        List<QuestionDTO> questionDTOs = new ArrayList<>();

        for (FaithTest faithTest : faithTests) {
            QuestionDTO dto = new QuestionDTO();
            try {
                // Set the question text based on the language
                if (language.equalsIgnoreCase("tagalog")) {
                    dto.setQuestionText(faithTest.getQuestionTextTagalog());
                    if (faithTest.getAnswerOptionsTagalog() != null) {
                        dto.setAnswerOptions(objectMapper.readValue(faithTest.getAnswerOptionsTagalog(), new TypeReference<List<String>>() {}));
                    }
                } else {
                    dto.setQuestionText(faithTest.getQuestionTextEnglish());
                    if (faithTest.getAnswerOptionsEnglish() != null) {
                        dto.setAnswerOptions(objectMapper.readValue(faithTest.getAnswerOptionsEnglish(), new TypeReference<List<String>>() {}));
                    }
                }
            } catch (JsonMappingException e) {
                e.printStackTrace(); // Log the error for debugging purposes
            } catch (JsonProcessingException e) {
                e.printStackTrace(); // Handle generic JSON processing errors
            }

            questionDTOs.add(dto);
        }

        return questionDTOs;
    }

    public List<FaithTestResponse> getResponsesByUser(Long userId) throws Exception {
        logger.info("Fetching responses for userId: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Exception("User not found: " + userId));
        List<FaithTestResponse> responses = faithTestResponseRepository.findByUser(user);

        if (responses.isEmpty()) {
            logger.warn("No responses found for userId: {}", userId);
        } else {
            logger.info("Found {} responses for userId: {}", responses.size(), userId);
        }

        return responses;
    }

    public void saveUserResponse(Long userId, Long testId, String selectedAnswer, String language) throws Exception {
        logger.info("Entering saveUserResponse method for userId: " + userId + ", testId: " + testId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Exception("User not found with ID: " + userId));
        FaithTest faithTest = faithTestRepository.findById(testId)
                .orElseThrow(() -> new Exception("FaithTest not found with ID: " + testId));

        FaithTestResponse response = new FaithTestResponse();
        response.setUser(user);
        response.setFaithTest(faithTest);

        // Check the language and store the response accordingly
        if (language.equalsIgnoreCase("tagalog")) {
            logger.info("Saving Tagalog response");
            response.setSelectedAnswerTagalog(selectedAnswer);
        } else if (language.equalsIgnoreCase("english")) {
            logger.info("Saving English response");
            response.setSelectedAnswerEnglish(selectedAnswer);
        } else {
            throw new Exception("Unsupported language: " + language);
        }

        faithTestResponseRepository.save(response);  // Persist the response
        logger.info("Response saved successfully for userId: " + userId + ", testId: " + testId);
    }

    // Method to check if the user has completed the faith test
    public boolean hasUserCompletedTest(Long userId) {
        // Check if there is a completion record in the `user_faith_test_status` table
        Optional<UserFaithTestStatus> status = userFaithTestStatusRepository.findByUserIdAndTestId(userId, 1L); // Assuming test_id is 1
        return status.isPresent() && status.get().isCompleted(); // Return true if completed, false otherwise
    }

    public void markTestAsCompleted(Long userId) {
        logger.info("Marking test as completed for userId: {}", userId);

        Optional<UserFaithTestStatus> status = userFaithTestStatusRepository.findByUserIdAndTestId(userId, 1L);
        if (status.isPresent()) {
            logger.info("User faith test status found, marking as completed.");
            UserFaithTestStatus existingStatus = status.get();
            existingStatus.setCompleted(true);
            userFaithTestStatusRepository.save(existingStatus);
            logger.info("Faith test marked as completed for userId: {}", userId);
        } else {
            logger.info("No previous faith test status found, creating a new record.");
            UserFaithTestStatus newStatus = new UserFaithTestStatus(userId, 1L, true); // Assuming test_id is 1
            userFaithTestStatusRepository.save(newStatus);
            logger.info("New faith test completion status created for userId: {}", userId);
        }
    }

    public List<User> getAllUsersWithCompletedFaithTest() {
        // Fetch all users who have completed the faith test
        List<UserFaithTestStatus> completedStatuses = userFaithTestStatusRepository.findByCompleted(true);
        List<User> users = new ArrayList<>();

        for (UserFaithTestStatus status : completedStatuses) {
            Optional<User> user = userRepository.findById(status.getUserId());
            user.ifPresent(users::add);
        }
        return users;
    }

    public List<FaithTestResponseDTO> getUserFaithTestResponses(Long userId) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(() -> new Exception("User not found"));
        List<FaithTestResponse> responses = faithTestResponseRepository.findByUser(user);

        List<FaithTestResponseDTO> responseDTOs = new ArrayList<>();
        for (FaithTestResponse response : responses) {
            FaithTestResponseDTO dto = new FaithTestResponseDTO();
            dto.setUserId(user.getId());
            dto.setTestId(response.getFaithTest().getTestId());
            dto.setSelectedAnswerEnglish(response.getSelectedAnswer()); // Assuming it's English, you can change based on need
            responseDTOs.add(dto);
        }

        return responseDTOs;
    }

    public FaithTestResponseDTO getFaithTestResponseById(Long responseId) throws Exception {
        FaithTestResponse response = faithTestResponseRepository.findById(responseId).orElseThrow(() -> new Exception("Response not found"));

        FaithTestResponseDTO dto = new FaithTestResponseDTO();
        dto.setUserId(response.getUser().getId());
        dto.setTestId(response.getFaithTest().getTestId());
        dto.setSelectedAnswerEnglish(response.getSelectedAnswer()); // Assuming English, can be changed
        return dto;
    }



    public List<UserFaithTestStatusDTO> getAllUsersWithFaithTestStatus() {
        List<User> allUsers = userRepository.findAll();
        List<UserFaithTestStatusDTO> userStatusList = new ArrayList<>();

        int totalQuestions = (int) faithTestRepository.count();


        for (User user : allUsers) {
            int userResponses = faithTestResponseRepository.countByUserId(user.getId());
            boolean hasCompleted = userResponses >= totalQuestions;

            UserFaithTestStatusDTO dto = new UserFaithTestStatusDTO(
                    user.getId(),
                    user.getName(),
                    user.getEmail(),
                    hasCompleted
            );
            userStatusList.add(dto);
        }

        return userStatusList;
    }





}
