package com.dobot.imjang.domain.media.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.dobot.imjang.domain.media.entities.Media;
import com.dobot.imjang.domain.media.repositories.MediaRepository;

@Service
public class MediaServiceImpl implements MediaService {

  @Value("${cloud.aws.s3.bucket}")
  private String bucket;
  private final AmazonS3 amazonS3;
  private final MediaRepository mediaRepository;

  public MediaServiceImpl(AmazonS3 amazonS3, MediaRepository mediaRepository) {
    this.amazonS3 = amazonS3;
    this.mediaRepository = mediaRepository;
  }

  @Override
  public Media uploadFile(MultipartFile multipartFile) {
    String originalFilename = multipartFile.getOriginalFilename();
    String uploadFilename = getUploadFilename(originalFilename);

    ObjectMetadata objectMetadata = new ObjectMetadata();
    objectMetadata.setContentLength(multipartFile.getSize());
    objectMetadata.setContentType(multipartFile.getContentType());

    try (InputStream inputStream = multipartFile.getInputStream()) {
      amazonS3.putObject(new PutObjectRequest(bucket, uploadFilename,
          inputStream, objectMetadata)
          .withCannedAcl(CannedAccessControlList.PublicRead));

    } catch (IOException e) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드 실패");
    }

    // 접근가능한 URL 가져오기
    String mediaUrl = amazonS3.getUrl(bucket, uploadFilename).toString();

    Media media = Media.builder().id(UUID.randomUUID()).fileType(multipartFile.getContentType())
        .mediaUrl(mediaUrl).originalFilename(originalFilename).build();
    mediaRepository.save(media);

    return media;
  }

  @Override
  public void deleteFile(String fileName) {
    amazonS3.deleteObject(new DeleteObjectRequest(bucket, fileName));
  }

  private String getUploadFilename(String fileName) {
    return UUID.randomUUID().toString().concat(getFileExtension(fileName));
  }

  private String getFileExtension(String fileName) {
    try {
      return fileName.substring(fileName.lastIndexOf("."));
    } catch (StringIndexOutOfBoundsException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 형식의 파일(" + fileName + ") 입니다.");
    }
  }
}