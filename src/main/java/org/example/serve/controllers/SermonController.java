package org.example.serve.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
// Remove or comment out the PreAuthorize import if needed for testing
// import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.example.serve.model.Sermon;
import org.example.serve.service.SermonService;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/sermons")
public class SermonController {

    private static final Logger logger = Logger.getLogger(SermonController.class.getName());

    @Autowired
    private SermonService sermonService;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @GetMapping
    public List<Sermon> getAllSermons() {
        return sermonService.getAllSermons();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sermon> getSermonById(@PathVariable Long id) {
        Optional<Sermon> sermon = sermonService.getSermonById(id);
        return sermon.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Remove or comment out the PreAuthorize annotations for testing
    // @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Sermon createSermon(@RequestBody Sermon sermon) {
        return sermonService.saveSermon(sermon);
    }

    // @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Sermon> updateSermon(@PathVariable Long id, @RequestBody Sermon updatedSermon) {
        Optional<Sermon> sermonOptional = sermonService.getSermonById(id);

        if (sermonOptional.isPresent()) {
            Sermon sermon = sermonOptional.get();
            sermon.setTitle(updatedSermon.getTitle());
            sermon.setDescription(updatedSermon.getDescription());
            sermon.setVideoUrl(updatedSermon.getVideoUrl());
            sermon.setAudioUrl(updatedSermon.getAudioUrl());
            sermon.setPdfUrl(updatedSermon.getPdfUrl());
            return ResponseEntity.ok(sermonService.saveSermon(sermon));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSermon(@PathVariable Long id) {
        sermonService.deleteSermon(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadSermon(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam(value = "video", required = false) MultipartFile video,
            @RequestParam(value = "audio", required = false) MultipartFile audio,
            @RequestParam(value = "pdf", required = false) MultipartFile pdf) {

        try {
            logger.info("Received upload request:");
            logger.info("Title: " + title);
            logger.info("Description: " + description);

            if (video != null) {
                logger.info("Video file: " + video.getOriginalFilename());
            }
            if (audio != null) {
                logger.info("Audio file: " + audio.getOriginalFilename());
            }
            if (pdf != null) {
                logger.info("PDF file: " + pdf.getOriginalFilename());
            }

            String videoUrl = null;
            String audioUrl = null;
            String pdfUrl = null;

            if (video != null && !video.isEmpty()) {
                logger.info("Saving video file...");
                videoUrl = saveFile(video);
            }

            if (audio != null && !audio.isEmpty()) {
                logger.info("Saving audio file...");
                audioUrl = saveFile(audio);
            }

            if (pdf != null && !pdf.isEmpty()) {
                logger.info("Saving PDF file...");
                pdfUrl = saveFile(pdf);
            }

            // Log the URLs
            logger.info("Video URL: " + videoUrl);
            logger.info("Audio URL: " + audioUrl);
            logger.info("PDF URL: " + pdfUrl);

            Sermon sermon = new Sermon();
            sermon.setTitle(title);
            sermon.setDescription(description);
            sermon.setVideoUrl(videoUrl);
            sermon.setAudioUrl(audioUrl);
            sermon.setPdfUrl(pdfUrl);

            sermon.setDate(java.time.LocalDateTime.now());

            sermonService.saveSermon(sermon);

            logger.info("Sermon saved successfully with ID: " + sermon.getId());

            return ResponseEntity.ok("Sermon uploaded successfully");

        } catch (Exception e) {
            logger.severe("Error uploading sermon: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error uploading sermon: " + e.getMessage());
        }
    }

    private String saveFile(MultipartFile file) throws IOException {
        // Sanitize file name
        String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
        originalFilename = originalFilename.replaceAll("[^a-zA-Z0-9\\.\\-]", "_");

        logger.info("Saving file: " + originalFilename);

        // Create directories if they don't exist
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            try {
                Files.createDirectories(uploadPath);
                logger.info("Created upload directory: " + uploadPath.toAbsolutePath());
            } catch (IOException e) {
                logger.severe("Failed to create upload directory: " + e.getMessage());
                throw e;
            }
        }

        // Save the file locally
        try {
            Path filePath = uploadPath.resolve(originalFilename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            logger.info("File saved to: " + filePath.toAbsolutePath());
        } catch (IOException e) {
            logger.severe("Failed to save file: " + e.getMessage());
            throw e;
        }

        // Return the file's URL
        return "/uploaded_files/" + originalFilename;
    }
}
