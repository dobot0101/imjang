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

  // 아래는 직접 구현하는 빌더 패턴
  // private Media(Builder builder) {
  // this.id = builder.id;
  // this.originalFilename = builder.originalFilename;
  // this.fileType = builder.fileType;
  // this.mediaUrl = builder.mediaUrl;
  // this.purpose = builder.purpose;
  // }

  // public static Builder builder() {
  // return new Builder();
  // }

  // public static class Builder {
  // private UUID id;
  // private String originalFilename;
  // private String fileType;
  // private String mediaUrl;
  // private String purpose;

  // public Builder withId(UUID id) {
  // this.id = id;
  // return this;
  // }

  // public Builder withOriginalFilename(String originalFilename) {
  // this.originalFilename = originalFilename;
  // return this;
  // }

  // public Builder withFileType(String fileType) {
  // this.fileType = fileType;
  // return this;
  // }

  // public Builder withMediaUrl(String mediaUrl) {
  // this.mediaUrl = mediaUrl;
  // return this;
  // }

  // public Builder withPurpose(String purpose) {
  // this.purpose = purpose;
  // return this;
  // }

  // public Media build() {
  // Media media = new Media(this);
  // return media;
  // }
  // }
}
