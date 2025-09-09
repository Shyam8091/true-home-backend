package com.example.true_home.service.impl;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.client.http.InputStreamContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTubeScopes;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoSnippet;
import com.google.api.services.youtube.model.VideoStatus;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Collections;

@Service
public class YouTubeUploader {

    private static final String CLIENT_SECRETS = "/client_secret.json";
    private static final Collection<String> SCOPES = Collections.singletonList(YouTubeScopes.YOUTUBE_UPLOAD);
    private static final String CREDENTIALS_FOLDER = "tokens";

    private static Credential authorize(final NetHttpTransport httpTransport) throws Exception {
        InputStream in = YouTubeUploader.class.getResourceAsStream(CLIENT_SECRETS);
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
                JacksonFactory.getDefaultInstance(), new InputStreamReader(in));

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, JacksonFactory.getDefaultInstance(), clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(CREDENTIALS_FOLDER)))
                .setAccessType("offline")
                .build();

        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8080).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    public static String uploadVideo(File videoFile, String title, String description) throws Exception {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        Credential credential = authorize(httpTransport);

        YouTube youtubeService = new YouTube.Builder(httpTransport, JacksonFactory.getDefaultInstance(), credential)
                .setApplicationName("youtube-uploader")
                .build();

        Video videoObjectDefiningMetadata = new Video();
        VideoSnippet snippet = new VideoSnippet();
        snippet.setTitle(title);
        snippet.setDescription(description);
        snippet.setCategoryId("22"); // e.g., "People & Blogs"
        videoObjectDefiningMetadata.setSnippet(snippet);

        VideoStatus status = new VideoStatus();
        status.setPrivacyStatus("unlisted"); // or unlisted/private
        videoObjectDefiningMetadata.setStatus(status);

        InputStreamContent mediaContent = new InputStreamContent(
                "video/*", new BufferedInputStream(new FileInputStream(videoFile)));
        mediaContent.setLength(videoFile.length());

        YouTube.Videos.Insert request = youtubeService.videos()
                .insert("snippet,status", videoObjectDefiningMetadata, mediaContent);

        MediaHttpUploader uploader = request.getMediaHttpUploader();
        uploader.setDirectUploadEnabled(false); // chunked upload

        Video uploadedVideo = request.execute();
        return uploadedVideo.getId();
    }
}
