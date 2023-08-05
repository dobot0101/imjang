package com.dobot.imjang.domain.unit.entities;

import java.util.UUID;

import com.dobot.imjang.domain.common.entities.BaseTime;
import com.dobot.imjang.domain.unit.enums.TransactionType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Setter;

@Entity
@Setter
public class UnitTransactionType extends BaseTime {
  @Id
  UUID id;

  @ManyToOne
  Unit unit;

  @Enumerated(EnumType.STRING)
  TransactionType transactionType;

}