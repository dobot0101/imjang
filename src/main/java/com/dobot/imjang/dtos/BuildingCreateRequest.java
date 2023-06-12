package com.dobot.imjang.dtos;

import java.util.List;

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
public class BuildingCreateRequest {

  // 건물(아파트, 빌라, 오피스텔 등)
  String name;
  String address;
  ElevatorStatus elevatorStatus;
  EntranceStructure entranceStructure;
  ParkingSpace parkingSpace;
  List<SchoolType> schoolTypes;
  List<FacilityType> facilityTypes;
  double latitude;
  double longitude;

  // 교통수단
  List<TransportationType> transportationTypes;

  // 호
  String buildingNumber; // 동
  String roomNumber; // 호
  Double area; // 면적
  String memo; // 메모
  TransactionType transactionType; // 매매, 전세, 월세
  Double transactionPrice;
  Double deposit; // 월세의 경우에만 사용, 월세 보증금
  Direction direction; // 남향, 남동향, 남서향, 동향, 서향, 북향
  ViewQuality viewQuality; // 좋음, 보통, 나쁨
  Ventilation ventilation; // 좋음, 나쁨
  WaterPressure waterPressure; // 좋음, 나쁨
  NoiseLevel noiseLevel; // 없음, 보통, 심함
  CondensationMoldLevel condensationMoldLevel; // 없음, 보통, 심함
  LeakStatus leakStatus; // 없음, 있음
  List<String> imageUrls;

}
