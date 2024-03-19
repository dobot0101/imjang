package com.dobot.imjang.domain.building;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.dobot.imjang.domain.building.enums.ElevatorStatus;
import com.dobot.imjang.domain.building.enums.EntranceStructure;
import com.dobot.imjang.domain.building.enums.FacilityType;
import com.dobot.imjang.domain.building.enums.ParkingSpace;
import com.dobot.imjang.domain.building.enums.SchoolType;
import com.dobot.imjang.domain.building.enums.TransportationType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor()
public class CreateBuildingDto implements IBuildingDto {
    // 좌표(위도)
    @NotNull(message = "위도를 입력해주세요")
    private double latitude;

    // 좌표(경도)
    @NotNull(message = "경도를 입력해주세요")
    private double longitude;

    // 건물(아파트, 빌라, 오피스텔 등)
    @NotBlank(message = "이름을 입력해주세요")
    @Length(max = 20)
    private String name;

    // 건물 주소
    @NotBlank(message = "주소를 입력해주세요")
    @Length(max = 100)
    private String address;

    // 엘리베이터 유무 및 옵션(지하주차장 연결 여부)
    @Enumerated(EnumType.STRING)
    private ElevatorStatus elevatorStatus;

    // 현관구조 (계단 / 복도)
    @Enumerated(EnumType.STRING)
    private EntranceStructure entranceStructure;

    // 주차공간(많음, 보통, 적음)
    @Enumerated(EnumType.STRING)
    private ParkingSpace parkingSpace;

    // 학군
    @Enumerated(EnumType.STRING)
    private List<SchoolType> schoolTypes;

    // 주변시설(편의점 등)
    @Enumerated(EnumType.STRING)
    private List<FacilityType> facilityTypes;

    // 교통수단
    @Enumerated(EnumType.STRING)
    private List<TransportationType> transportationTypes;
}
