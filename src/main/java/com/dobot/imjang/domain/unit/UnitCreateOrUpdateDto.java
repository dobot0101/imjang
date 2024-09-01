package com.dobot.imjang.domain.unit;

import java.util.ArrayList;
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
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UnitCreateOrUpdateDto {
    // 동
    @NotBlank(message = "동을 입력하세요.")
    private String buildingNumber;

    // 호
    @NotBlank(message = "호를 입력하세요.")
    private String roomNumber;

    // 면적
    @Size(min = 0, max = 10, message = "면적은 0자 이상 10자 이하로 입력하세요.")
    private String area;

    // 메모
    @Size(min = 0, max = 100, message = "메모는 0자 이상 100자 이하로 입력하세요.")
    private String memo;

    // 월세가
    @Min(value = 0, message = "월세 가격은 0 이상의 숫자를 입력하세요.")
    private Integer monthlyPrice;

    // 전세가
    @Min(value = 0, message = "전세 가격은 0 이상의 숫자를 입력하세요.")
    private Integer jeonsePrice;

    // 매매가
    @Min(value = 0, message = "매매 가격은 0 이상의 숫자를 입력하세요.")
    private Integer salePrice;

    // 융자금
    @Min(value = 0, message = "융자금은 0 이상의 숫자를 입력하세요.")
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
    // @Builder 어노테이션은 초기화를 무시하기 때문에 초기화하려면 아래와 같이 필드에 final을 붙이면 된다.
    private final List<UUID> uploadedFileIds = new ArrayList<>();
}
