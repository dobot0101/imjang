package com.dobot.imjang.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import com.dobot.imjang.enums.TransactionType;

import lombok.Setter;

@Entity
@Setter
public class UnitTransactionType {
  @Id
  UUID id;

  @ManyToOne
  Unit unit;

  @Enumerated(EnumType.STRING)
  TransactionType transactionType;

  @CreationTimestamp
  @Column(nullable = false, updatable = false)
  LocalDateTime createdAt;
}