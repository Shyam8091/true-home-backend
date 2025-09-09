package com.example.true_home.controller;

import com.example.true_home.service.impl.YouTubeUploader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("/api/youtube")
public class YouTubeUploadController {

    @PostMapping("/upload")
    public ResponseEntity<String> uploadVideo(
            @RequestParam("file") MultipartFile file,
            @RequestParam("title") String title,
            @RequestParam("description") String description
    ) {
        try {
            // Convert MultipartFile to File
            File videoFile = convertMultipartFileToFile(file);

            // Call service method
            String youtubeUrl = YouTubeUploader.uploadVideo(videoFile, title, description);

            // Cleanup temporary file
            videoFile.delete();

            return ResponseEntity.ok("Video uploaded successfully: " + youtubeUrl);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Video upload failed: " + e.getMessage());
        }
    }

    private File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        File file = File.createTempFile("youtube_upload_", "_" + multipartFile.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(multipartFile.getBytes());
        }
        return file;
    }
}
