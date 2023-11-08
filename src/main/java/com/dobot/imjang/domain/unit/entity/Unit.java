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
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
  private UUID id;

  // 동
  @Column(length = 20)
  private String buildingNumber;

  // 호
  @Column(length = 20)
  private String roomNumber;

  // 면적
  @Column(length = 20)
  private String area;

  // 메모
  @Column(columnDefinition = "TEXT")
  private String memo;

  // 융자금
  private Integer deposit;

  // 월세가
  private Integer monthlyPrice;

  // 전세가
  private Integer jeonsePrice;

  // 매매가
  private Integer salePrice;

  // 집 방향(남향, 남동향, 남서향, 동향, 서향, 북향)
  @Column(length = 20)
  @Enumerated(EnumType.STRING)
  private Direction direction;

  // 전망(좋음, 보통, 나쁨)
  @Column(length = 20)
  @Enumerated(EnumType.STRING)
  private ViewQuality viewQuality;

  // 통풍(좋음, 나쁨)
  @Column(length = 20)
  @Enumerated(EnumType.STRING)
  private Ventilation ventilation;

  // 수압(좋음, 나쁨)
  @Column(length = 20)
  @Enumerated(EnumType.STRING)
  private WaterPressure waterPressure;

  // 소음(없음, 보통, 심함)
  @Column(length = 20)
  @Enumerated(EnumType.STRING)
  private NoiseLevel noiseLevel;

  // 결로, 곰팡이(없음, 보통, 심함)
  @Column(length = 20)
  @Enumerated(EnumType.STRING)
  private CondensationMoldLevel condensationMoldLevel;

  // 누수(없음, 있음)
  @Column(length = 20)
  @Enumerated(EnumType.STRING)
  private LeakStatus leakStatus;

  @JsonBackReference
  @ManyToOne()
  @JoinColumn(name = "building_id")
  private Building building;

  @JsonManagedReference
  @OneToMany(mappedBy = "unit", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
  private List<UnitImage> images = new ArrayList<>();
}