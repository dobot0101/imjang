package com.dobot.imjang.domain.common.entities;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.annotation.Nonnull;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTime {
  @CreatedDate()
  @Nonnull
  LocalDateTime createdAt;

  @LastModifiedDate
  LocalDateTime modifiedAt;

  public LocalDateTime getCreatedDate() {
    return createdAt;
  }

  public LocalDateTime getModifiedDate() {
    return modifiedAt;
  }
}