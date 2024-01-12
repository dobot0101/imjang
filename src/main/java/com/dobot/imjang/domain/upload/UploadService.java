package com.dobot.imjang.domain.upload;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
  public UploadResult uploadFile(MultipartFile multipartFile);

  public void deleteFile(String fileName);
}