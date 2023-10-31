package com.dobot.imjang.domain.unit.entities;

import java.util.UUID;

import com.dobot.imjang.domain.common.entities.BaseTime;
import com.dobot.imjang.domain.unit.enums.TransactionType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;

@Entity
@AllArgsConstructor
public class UnitTransaction extends BaseTime {
  @Id
  private UUID id;

  // 거래 가격
  @Column(nullable = false)
  private Integer price;

  // 월세의 경우에만 사용, 월세 보증금
  @Column(nullable = false)
  private Integer deposit;

  @ManyToOne
  private Unit unit;

  @Enumerated(EnumType.STRING)
  private TransactionType transactionType;
}