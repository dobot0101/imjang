package com.dobot.imjang.dtos;

import java.util.List;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.dobot.imjang.enums.ElevatorStatus;
import com.dobot.imjang.enums.EntranceStructure;
import com.dobot.imjang.enums.FacilityType;
import com.dobot.imjang.enums.ParkingSpace;
import com.dobot.imjang.enums.SchoolType;
import com.dobot.imjang.enums.TransportationType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BuildingRequest {
  // 건물(아파트, 빌라, 오피스텔 등)
  @NotBlank(message = "이름을 입력해주세요")
  @Length(max = 20)
  String name;

  // 건물 주소
  @NotBlank(message = "주소를 입력해주세요")
  @Length(max = 100)
  String address;

  // 엘리베이터 유무 및 옵션(지하주차장 연결 여부)
  @Enumerated(EnumType.STRING)
  ElevatorStatus elevatorStatus;

  // 현관구조 (계단 / 복도)
  @Enumerated(EnumType.STRING)
  EntranceStructure entranceStructure;

  // 주차공간(많음, 보통, 적음)
  @Enumerated(EnumType.STRING)
  ParkingSpace parkingSpace;

  // 학군
  @Enumerated(EnumType.STRING)
  List<SchoolType> schoolTypes;

  // 주변시설(편의점 등)
  @Enumerated(EnumType.STRING)
  List<FacilityType> facilityTypes;

  // 좌표(위도)
  @NotNull(message = "위도를 입력해주세요")
  double latitude;

  // 좌표(경도)
  @NotNull(message = "경도를 입력해주세요")
  double longitude;

  // 교통수단
  @Enumerated(EnumType.STRING)
  List<TransportationType> transportationTypes;
}
