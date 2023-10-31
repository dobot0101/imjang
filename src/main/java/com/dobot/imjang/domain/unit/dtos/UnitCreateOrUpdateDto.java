package com.dobot.imjang.domain.unit.dtos;

import java.util.List;
import java.util.UUID;

import com.dobot.imjang.domain.unit.enums.CondensationMoldLevel;
import com.dobot.imjang.domain.unit.enums.Direction;
import com.dobot.imjang.domain.unit.enums.LeakStatus;
import com.dobot.imjang.domain.unit.enums.NoiseLevel;
import com.dobot.imjang.domain.unit.enums.Ventilation;
import com.dobot.imjang.domain.unit.enums.ViewQuality;
import com.dobot.imjang.domain.unit.enums.WaterPressure;

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
public class UnitCreateOrUpdateDto {
  @NotBlank
  String buidlingId;
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

  // 거래 정보(계약 유형, 금액, 융자금 등)
  @NotNull
  @Size(max = 1, message = "거래 정보를 1개 이상 입력해주세요.")
  List<UnitTransactionDto> unitTransactionDtos;

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
