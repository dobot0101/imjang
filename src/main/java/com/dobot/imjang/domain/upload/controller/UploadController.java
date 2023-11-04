package com.dobot.imjang.domain.upload.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.dobot.imjang.domain.upload.entity.UploadResult;
import com.dobot.imjang.domain.upload.service.UploadService;

@Controller
@RequestMapping("/api/upload")
class UploadController {
  private final UploadService uploadService;

  public UploadController(UploadService uploadService) {
    this.uploadService = uploadService;
  }

  @PostMapping
  public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
    Map<String, String> map = new HashMap<>();
    if (file.isEmpty()) {
      map.put("error", "Please select a file to upload");
      return ResponseEntity.badRequest().body(map);
    }

    try {
      UploadResult upload = this.uploadService.uploadFile(file);
      map.put("accessMediaUrl", upload.getMediaUrl());
      return ResponseEntity.ok(map);
    } catch (Exception e) {
      e.printStackTrace();
      map.put("error", "File upload failed");
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
    }
  }
}