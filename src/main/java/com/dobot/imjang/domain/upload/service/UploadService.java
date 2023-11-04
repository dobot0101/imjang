package com.dobot.imjang.domain.upload.service;

import org.springframework.web.multipart.MultipartFile;

import com.dobot.imjang.domain.upload.entity.UploadResult;

public interface UploadService {
  public UploadResult uploadFile(MultipartFile multipartFile);

  public void deleteFile(String fileName);
}