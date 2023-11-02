package com.dobot.imjang.domain.unit.dto;

import java.util.ArrayList;
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
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UnitCreateOrUpdateDto {
  private String buildingId;
  // 동
  @NotBlank
  private String buildingNumber;

  // 호
  @NotBlank
  private String roomNumber;

  // 면적
  @DecimalMin(inclusive = true, message = "면적은 0 이상이어야 합니다.", value = "0.0")
  private Double area;

  // 메모
  @Size(min = 0, max = 100)
  private String memo;

  // 월세가
  @Min(value = 0, message = "0 이상의 숫자를 입력하세요.")
  private Integer monthlyPrice;

  // 전세가
  @Min(value = 0, message = "0 이상의 숫자를 입력하세요.")
  private Integer jeonsePrice;

  // 매매가
  @Min(value = 0, message = "0 이상의 숫자를 입력하세요.")
  private Integer salePrice;

  // 융자금
  @Min(value = 0, message = "0 이상의 숫자를 입력하세요.")
  private Integer deposit;

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
  private List<String> imageUrls = new ArrayList<>();
}
