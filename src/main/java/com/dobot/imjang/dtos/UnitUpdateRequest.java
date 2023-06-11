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

public class UnitUpdateRequest {
  UUID id;
  UUID buidlingId;
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

  public UUID getId() {
    return id;
  }

  public UUID getBuidlingId() {
    return buidlingId;
  }

  public String getBuildingNumber() {
    return buildingNumber;
  }

  public String getRoomNumber() {
    return roomNumber;
  }

  public Double getArea() {
    return area;
  }

  public String getMemo() {
    return memo;
  }

  public TransactionType getTransactionType() {
    return transactionType;
  }

  public Double getTransactionPrice() {
    return transactionPrice;
  }

  public Double getDeposit() {
    return deposit;
  }

  public Direction getDirection() {
    return direction;
  }

  public ViewQuality getViewQuality() {
    return viewQuality;
  }

  public Ventilation getVentilation() {
    return ventilation;
  }

  public WaterPressure getWaterPressure() {
    return waterPressure;
  }

  public NoiseLevel getNoiseLevel() {
    return noiseLevel;
  }

  public CondensationMoldLevel getCondensationMoldLevel() {
    return condensationMoldLevel;
  }

  public LeakStatus getLeakStatus() {
    return leakStatus;
  }

  public List<String> getImageUrls() {
    return imageUrls;
  }

}
