package com.dobot.imjang.domain.upload.entity;

import java.util.UUID;

import com.dobot.imjang.domain.common.entities.BaseTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Entity
public class UploadResult extends BaseTime {
  @Id
  UUID id;

  @Column(nullable = false, length = 255)
  String originalFilename;

  @Column(nullable = false, length = 30)
  String fileType;

  @Column(nullable = false, length = 255)
  String fileUrl;
}


