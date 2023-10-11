package com.dobot.imjang.domain.media.services;

import org.springframework.web.multipart.MultipartFile;

import com.dobot.imjang.domain.media.entities.Media;

public interface MediaService {
  public Media uploadFile(MultipartFile multipartFile);

  public void deleteFile(String fileName);
}