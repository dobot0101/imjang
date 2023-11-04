package com.dobot.imjang.domain.unit.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.dobot.imjang.domain.building.entity.Building;
import com.dobot.imjang.domain.common.entities.BaseTime;
import com.dobot.imjang.domain.unit.enums.CondensationMoldLevel;
import com.dobot.imjang.domain.unit.enums.Direction;
import com.dobot.imjang.domain.unit.enums.LeakStatus;
import com.dobot.imjang.domain.unit.enums.NoiseLevel;
import com.dobot.imjang.domain.unit.enums.Ventilation;
import com.dobot.imjang.domain.unit.enums.ViewQuality;
import com.dobot.imjang.domain.unit.enums.WaterPressure;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
  @Column(columnDefinition = "TEXT")
  String memo;

  // 융자금
  private Integer deposit;

  // 월세가
  private Integer monthlyPrice;

  // 전세가
  private Integer jeonsePrice;

  // 매매가
  private Integer salePrice;

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
  @JoinColumn(name = "building_id")
  Building building;

  @OneToMany(mappedBy = "unit", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
  List<UnitImage> images = new ArrayList<>();

  // @ManyToOne()
  // @JoinColumn(name = "member_id")
  // private Member member;
}