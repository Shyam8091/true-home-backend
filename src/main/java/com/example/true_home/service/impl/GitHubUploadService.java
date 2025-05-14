package com.example.true_home.service.impl;

import com.example.true_home.feign.GitHubFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class GitHubUploadService {

    @Value("github_sec")
    private String GITHUB_TOKEN; // Your GitHub Personal Access Token

    @Autowired
    private GitHubFeignClient gitHubFeignClient;



    public String uploadImageToGitHub(String base64Content, String owner, String repo, String path)  {


        // Prepare the request body
        Map<String, Object> fileData = new HashMap<>();
        fileData.put("message", "Uploaded image via FeignClient");
        fileData.put("content", base64Content);

        // Call GitHub API using FeignClient
        ResponseEntity<Map<String, Object>> response = gitHubFeignClient.uploadFile(
                owner, repo, path, fileData, "token " + GITHUB_TOKEN
        );
        System.out.println("response"+response);
        // Check the response and return the URL
        if (response.getStatusCode().is2xxSuccessful()) {
            Map<String, Object> responseBody = response.getBody();
            if (responseBody != null && responseBody.containsKey("content")) {
                Map<String, Object> content = (Map<String, Object>) responseBody.get("content");
                return (String) content.get("download_url");
            }
        }
        try {
            throw new IOException("Failed to upload image to GitHub");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
