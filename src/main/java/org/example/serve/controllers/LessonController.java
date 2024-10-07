package org.example.serve.controllers;



import org.example.serve.model.Lesson;
import org.example.serve.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.serve.model.User;
import org.example.serve.service.UserService;
import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.*;
import java.io.*;

import java.nio.file.*;
import java.util.UUID;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
@RestController
@RequestMapping("/api/lessons")
public class LessonController {


    @Autowired
    private LessonService lessonService;

    @Autowired
    private UserService userService;  // Add this line

    @PostMapping
    public ResponseEntity<?> createLesson(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("content") String content,
            @RequestParam("scriptureReference") String scriptureReference,
            @RequestParam(value = "image", required = false) MultipartFile imageFile,
            @RequestParam Long userId
    ) {
        // Validate and process inputs
        // ... existing validation code ...

        // Retrieve the user by userId
        User user = userService.findUserById(userId);
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }

        // Handle image upload
        String imageUrl = null;
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                // Save the image to the server and get the URL
                imageUrl = saveImage(imageFile);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving image: " + e.getMessage());
            }
        }

        // Create and save the lesson
        Lesson lesson = new Lesson();
        lesson.setTitle(title);
        lesson.setDescription(description);
        lesson.setContent(content);
        lesson.setScriptureReference(scriptureReference);
        lesson.setUser(user);  // Now 'user' is defined
        lesson.setImageUrl(imageUrl);

        try {
            Lesson savedLesson = lessonService.saveLesson(lesson);
            return ResponseEntity.ok(savedLesson);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error saving lesson: " + e.getMessage());
        }
    }


    // Method to save the image and return its URL
    private String saveImage(MultipartFile imageFile) throws IOException {
        // Define the directory where you want to save images
        String uploadDir = "uploads/images/";

        // Create the directory if it doesn't exist
        File uploadDirFile = new File(uploadDir);
        if (!uploadDirFile.exists()) {
            uploadDirFile.mkdirs();
        }

        // Generate a unique filename
        String filename = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();

        // Save the file to the server
        Path filePath = Paths.get(uploadDir, filename);
        Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Return the URL to access the image
        return "/api/lessons/images/" + filename;
    }

    // Endpoint to serve images
    @GetMapping("/images/{filename:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        try {
            Path filePath = Paths.get("uploads/images/", filename);
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() || resource.isReadable()) {
                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_TYPE, Files.probeContentType(filePath));
                return new ResponseEntity<>(resource, headers, HttpStatus.OK);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }




    // GET endpoint to retrieve all lessons
    @GetMapping
    public ResponseEntity<List<Lesson>> getAllLessons() {
        List<Lesson> lessons = lessonService.findAllLessons();
        return ResponseEntity.ok(lessons);
    }

    // GET endpoint to retrieve a single lesson by ID
    @GetMapping("/{id}")
    public ResponseEntity<Lesson> getLessonById(@PathVariable Long id) {
        Lesson lesson = lessonService.findLessonById(id);
        return lesson != null ? ResponseEntity.ok(lesson) : ResponseEntity.notFound().build();
    }


    // DELETE endpoint to delete a lesson
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLesson(@PathVariable Long id) {
        boolean isDeleted = lessonService.deleteLesson(id);
        return isDeleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    //GetAll Endpoint 
}
