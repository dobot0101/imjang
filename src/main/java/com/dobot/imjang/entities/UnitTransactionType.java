package com.dobot.imjang.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

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
  LocalDateTime createdDate;
}