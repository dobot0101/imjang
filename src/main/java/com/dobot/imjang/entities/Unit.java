package com.dobot.imjang.entities;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import com.dobot.imjang.enums.CondensationMoldLevel;
import com.dobot.imjang.enums.Direction;
import com.dobot.imjang.enums.LeakStatus;
import com.dobot.imjang.enums.NoiseLevel;
import com.dobot.imjang.enums.Ventilation;
import com.dobot.imjang.enums.ViewQuality;
import com.dobot.imjang.enums.WaterPressure;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(indexes = {
    @Index(columnList = "building_id")
})
public class Unit extends BaseTime {
  @Id
  UUID id;

  // 동
  String buildingNumber;

  // 호
  String roomNumber;

  // 면적
  Double area;

  // 메모
  String memo;

  // 매매, 전세, 월세
  @OneToMany(mappedBy = "unit", cascade = CascadeType.ALL, orphanRemoval = true)
  List<UnitTransactionType> transactionTypes;

  // 거래 가격
  Double transactionPrice;

  // 월세의 경우에만 사용, 월세 보증금
  Double deposit;

  // 집 방향(남향, 남동향, 남서향, 동향, 서향, 북향)
  @Enumerated(EnumType.STRING)
  Direction direction;

  // 전망(좋음, 보통, 나쁨)
  @Enumerated(EnumType.STRING)
  ViewQuality viewQuality;

  // 통풍(좋음, 나쁨)
  @Enumerated(EnumType.STRING)
  Ventilation ventilation;

  // 수압(좋음, 나쁨)
  @Enumerated(EnumType.STRING)
  WaterPressure waterPressure;

  // 소음(없음, 보통, 심함)
  @Enumerated(EnumType.STRING)
  NoiseLevel noiseLevel;

  // 결로, 곰팡이(없음, 보통, 심함)
  @Enumerated(EnumType.STRING)
  CondensationMoldLevel condensationMoldLevel;

  // 누수(없음, 있음)
  @Enumerated(EnumType.STRING)
  LeakStatus leakStatus;

  @ManyToOne()
  Building building;

  @OneToMany(mappedBy = "unit", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
  List<UnitImage> images;
}