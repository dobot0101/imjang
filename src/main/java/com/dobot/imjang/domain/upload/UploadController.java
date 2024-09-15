package com.dobot.imjang.domain.upload;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/api/upload")
@RequiredArgsConstructor
class UploadController {
    private final UploadService uploadService;
    private final ObjectMapper objectMapper;

    @PostMapping
    public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        Map<String, String> map = new HashMap<>();
        if (file.isEmpty()) {
            map.put("error", "Please select a file to upload");
            return ResponseEntity.badRequest().body(map);
        }

        try {
            UploadResult result = this.uploadService.uploadFile(file);
            map.put("result", objectMapper.writeValueAsString(result));
            return ResponseEntity.ok(map);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("error", "File upload failed");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
        }
    }
}