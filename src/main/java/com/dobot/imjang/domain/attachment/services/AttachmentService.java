package com.dobot.imjang.domain.attachment.services;

import org.springframework.web.multipart.MultipartFile;

import com.dobot.imjang.domain.attachment.entities.Attachment;

public interface AttachmentService {
  public Attachment uploadFile(MultipartFile multipartFile);

  public void deleteFile(String fileName);
}