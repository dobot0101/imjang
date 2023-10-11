package com.dobot.imjang.domain.media.controllers;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.dobot.imjang.domain.media.entities.Media;
import com.dobot.imjang.domain.media.services.MediaService;

@Controller
class MediaController {
  private final MediaService mediaService;

  public MediaController(MediaService mediaService) {
    this.mediaService = mediaService;
  }

  @PostMapping("/upload")
  public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
    if (file.isEmpty()) {
      return ResponseEntity.badRequest().body("Please select a file to upload");
    }

    try {
      Media media = this.mediaService.uploadFile(file);
      return ResponseEntity.ok(media.getMediaUrl());
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(500).body("File upload failed");
    }
  }
}