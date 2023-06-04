package com.dobot.imjang.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.dobot.imjang.enums.CondensationMoldLevel;
import com.dobot.imjang.enums.Direction;
import com.dobot.imjang.enums.LeakStatus;
import com.dobot.imjang.enums.NoiseLevel;
import com.dobot.imjang.enums.Ventilation;
import com.dobot.imjang.enums.ViewQuality;
import com.dobot.imjang.enums.WaterPressure;

@Entity
public class Unit {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  String buildingNumber; // 동
  String roomNumber; // 호
  Double area; // 면적
  String memo; // 메모

  @Enumerated(EnumType.STRING)
  TransactionType transactionType; // 매매, 전세, 월세
  Double transactionPrice;
  Double deposit; // 월세의 경우에만 사용, 월세 보증금

  @Enumerated(EnumType.STRING)
  Direction direction; // 남향, 남동향, 남서향, 동향, 서향, 북향

  @Enumerated(EnumType.STRING)
  ViewQuality viewQuality; // 좋음, 보통, 나쁨

  @Enumerated(EnumType.STRING)
  Ventilation ventilation; // 좋음, 나쁨

  @Enumerated(EnumType.STRING)
  WaterPressure waterPressure; // 좋음, 나쁨

  @Enumerated(EnumType.STRING)
  NoiseLevel noiseLevel; // 없음, 보통, 심함

  @Enumerated(EnumType.STRING)
  CondensationMoldLevel condensationMoldLevel; // 없음, 보통, 심함

  @Enumerated(EnumType.STRING)
  private LeakStatus leakStatus; // 없음, 있음

  @ManyToOne
  @JoinColumn(name = "building_id")
  Building building;

  @OneToMany(mappedBy = "unit", cascade = CascadeType.ALL)
  List<UnitImage> images;

  // Getters and setters...
}