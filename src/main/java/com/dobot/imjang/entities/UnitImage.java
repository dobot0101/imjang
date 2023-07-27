package com.dobot.imjang.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Setter;

@Setter
@Entity
public class UnitImage {
  @Id
  UUID id;

  @Column(nullable = false)
  String imageUrl;

  @ManyToOne
  Unit unit;

  @CreationTimestamp
  @Column(nullable = false, updatable = false)
  LocalDateTime createdDate;
}