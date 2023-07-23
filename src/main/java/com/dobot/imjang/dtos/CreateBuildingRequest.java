package com.dobot.imjang.dtos;

import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.dobot.imjang.enums.CondensationMoldLevel;
import com.dobot.imjang.enums.Direction;
import com.dobot.imjang.enums.ElevatorStatus;
import com.dobot.imjang.enums.EntranceStructure;
import com.dobot.imjang.enums.FacilityType;
import com.dobot.imjang.enums.LeakStatus;
import com.dobot.imjang.enums.NoiseLevel;
import com.dobot.imjang.enums.ParkingSpace;
import com.dobot.imjang.enums.SchoolType;
import com.dobot.imjang.enums.TransactionType;
import com.dobot.imjang.enums.TransportationType;
import com.dobot.imjang.enums.Ventilation;
import com.dobot.imjang.enums.ViewQuality;
import com.dobot.imjang.enums.WaterPressure;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateBuildingRequest {
  // 건물(아파트, 빌라, 오피스텔 등)
  @NotBlank(message = "이름을 입력해주세요")
  String name;

  // 건물 주소
  @NotBlank(message = "주소를 입력해주세요")
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
