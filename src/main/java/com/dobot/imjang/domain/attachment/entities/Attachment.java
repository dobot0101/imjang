package com.dobot.imjang.domain.attachment.entities;

import java.util.UUID;

import com.dobot.imjang.domain.common.entities.BaseTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Attachment extends BaseTime {
  @Builder
  public Attachment(UUID id, String originalFilename, String fileType, String mediaUrl) {
    this.id = id;
    this.originalFilename = originalFilename;
    this.fileType = fileType;
    this.mediaUrl = mediaUrl;
  }

  @Id()
  UUID id;
  @Column(nullable = false)
  String originalFilename;
  @Column(nullable = false)
  String fileType;
  @Column(nullable = false)
  String mediaUrl;

  public String getMediaUrl() {
    return mediaUrl;
  }
}
