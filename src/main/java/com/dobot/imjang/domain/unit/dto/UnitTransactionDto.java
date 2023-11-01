package com.dobot.imjang.domain.unit.dto;

import com.dobot.imjang.domain.unit.enums.TransactionType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class UnitTransactionDto {
  private Integer price;
  private Integer deposit;
  private TransactionType transactionType;
}
