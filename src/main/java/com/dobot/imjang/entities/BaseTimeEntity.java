package com.dobot.imjang.entities;

import java.time.LocalDateTime;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {
  @CreatedDate
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