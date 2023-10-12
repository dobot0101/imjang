package com.dobot.imjang.domain.attachment.controllers;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.dobot.imjang.domain.attachment.entities.Attachment;
import com.dobot.imjang.domain.attachment.services.AttachmentService;

@Controller
class AttachmentController {
  private final AttachmentService attahmentService;

  public AttachmentController(AttachmentService attachmentService) {
    this.attahmentService = attachmentService;
  }

  @PostMapping("/upload")
  public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
    if (file.isEmpty()) {
      return ResponseEntity.badRequest().body("Please select a file to upload");
    }

    try {
      Attachment attachment = this.attahmentService.uploadFile(file);
      return ResponseEntity.ok(attachment.getMediaUrl());
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(500).body("File upload failed");
    }
  }
}