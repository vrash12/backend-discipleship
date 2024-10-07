package org.example.serve.service;

import org.example.serve.model.Lesson;
import org.example.serve.repositories.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LessonService {

    @Autowired
    private LessonRepository lessonRepository;

    // Save a new lesson or update an existing one
    public Lesson saveLesson(Lesson lesson) {
        return lessonRepository.save(lesson);
    }

    // Retrieve all lessons from the database
    public List<Lesson> findAllLessons() {
        return lessonRepository.findAll();
    }

    // Find a single lesson by ID
    public Lesson findLessonById(Long id) {
        return lessonRepository.findById(id).orElse(null);
    }

    // Update an existing lesson
    @Transactional
    public Lesson updateLesson(Long id, Lesson lessonDetails) {
        Optional<Lesson> optionalLesson = lessonRepository.findById(id);
        if (optionalLesson.isPresent()) {
            Lesson existingLesson = optionalLesson.get();
            existingLesson.setTitle(lessonDetails.getTitle());
            existingLesson.setDescription(lessonDetails.getDescription());
            existingLesson.setContent(lessonDetails.getContent());
            existingLesson.setUser(lessonDetails.getUser()); // Assuming a setter for user
            // Handle other fields as necessary
            return lessonRepository.save(existingLesson);
        }
        return null;
    }

    // Delete a lesson from the database
    public boolean deleteLesson(Long id) {
        if (lessonRepository.existsById(id)) {
            lessonRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
