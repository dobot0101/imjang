package com.dobot.imjang.domain.unit.dto;

import java.util.List;

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
  private String buidlingId;
  // 동
  @NotBlank
  private String buildingNumber;

  // 호
  @NotBlank
  private String roomNumber;

  // 면적
  private Double area;

  // 메모
  @Max(value = 100)
  private String memo;

  private Integer monthlyPrice;
  private Integer jeonsePrice;
  private Integer salePrice;

  // 거래 정보(계약 유형, 금액, 융자금 등)
  @NotNull
  @Size(max = 1, message = "거래 정보를 1개 이상 입력해주세요.")
  private List<UnitTransactionDto> unitTransactionDtos;

  // 집 방향(ex: 남향 등)
  @Enumerated(EnumType.STRING)
  private Direction direction;

  // 뷰(전망)
  @Enumerated(EnumType.STRING)
  private ViewQuality viewQuality;

  // 통풍
  @Enumerated(EnumType.STRING)
  private Ventilation ventilation;

  // 수압
  @Enumerated(EnumType.STRING)
  private WaterPressure waterPressure;

  // 소음
  @Enumerated(EnumType.STRING)
  private NoiseLevel noiseLevel;

  // 결로, 곰팡이
  @Enumerated(EnumType.STRING)
  private CondensationMoldLevel condensationMoldLevel;

  // 누수
  @Enumerated(EnumType.STRING)
  private LeakStatus leakStatus;

  // 첨부 이미지 url
  private List<String> imageUrls;
}
