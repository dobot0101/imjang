package com.dobot.imjang.dtos;

import java.util.List;
import java.util.UUID;

import com.dobot.imjang.enums.CondensationMoldLevel;
import com.dobot.imjang.enums.Direction;
import com.dobot.imjang.enums.LeakStatus;
import com.dobot.imjang.enums.NoiseLevel;
import com.dobot.imjang.enums.TransactionType;
import com.dobot.imjang.enums.Ventilation;
import com.dobot.imjang.enums.ViewQuality;
import com.dobot.imjang.enums.WaterPressure;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UnitRequest {
  @NotBlank
  UUID buidlingId;
  // 동
  @NotBlank
  String buildingNumber;

  // 호
  @NotBlank
  String roomNumber;

  // 면적
  Double area;

  // 메모
  @Max(value = 100)
  String memo;

  // 매매, 전세, 월세
  @NotNull(message = "계약방식을 1개 이상 선택해주세요")
  @Size(min = 1)
  @Enumerated(EnumType.STRING)
  List<TransactionType> transactionTypes;

  // 가격
  Double transactionPrice;

  // 월세의 경우에만 사용, 월세 보증금
  Double deposit;

  // 집 방향(ex: 남향 등)
  @Enumerated(EnumType.STRING)
  Direction direction;

  // 뷰(전망)
  @Enumerated(EnumType.STRING)
  ViewQuality viewQuality;

  // 통풍
  @Enumerated(EnumType.STRING)
  Ventilation ventilation;

  // 수압
  @Enumerated(EnumType.STRING)
  WaterPressure waterPressure;

  // 소음
  @Enumerated(EnumType.STRING)
  NoiseLevel noiseLevel;

  // 결로, 곰팡이
  @Enumerated(EnumType.STRING)
  CondensationMoldLevel condensationMoldLevel;

  // 누수
  @Enumerated(EnumType.STRING)
  LeakStatus leakStatus;

  List<String> imageUrls;
}
