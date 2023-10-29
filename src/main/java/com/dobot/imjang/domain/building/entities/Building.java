package com.dobot.imjang.domain.building.entities;

import java.util.List;
import java.util.UUID;

import com.dobot.imjang.domain.building.enums.ElevatorStatus;
import com.dobot.imjang.domain.building.enums.EntranceStructure;
import com.dobot.imjang.domain.building.enums.ParkingSpace;
import com.dobot.imjang.domain.common.entities.BaseTime;
import com.dobot.imjang.domain.member.entities.Member;
import com.dobot.imjang.domain.unit.entities.Unit;

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
    @Index(columnList = "latitude, longitude", unique = true)
})
public class Building extends BaseTime {
  @Id
  UUID id;

  // 좌표(위도)
  @Column(nullable = false)
  private double latitude;

  // 좌표(경도)
  @Column(nullable = false)
  private double longitude;

  // 건물 이름 (예: 아파트 단지 이름)
  @Column(nullable = false, length = 20)
  String name;

  // 건물 주소
  @Column(nullable = false, length = 100)
  String address;

  // 엘리베이터(있음, 없음)
  @Column()
  @Enumerated(EnumType.STRING)
  ElevatorStatus elevatorStatus;

  // 현관구조(계단식, 복도식)
  @Column()
  @Enumerated(EnumType.STRING)
  EntranceStructure entranceStructure;

  // 주차공간(많음, 적음, 보통)
  @Column()
  @Enumerated(EnumType.STRING)
  ParkingSpace parkingSpace;

  // 학군
  @OneToMany(mappedBy = "building", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  List<SchoolDistrict> schoolDistricts;

  // 주변시설
  @OneToMany(mappedBy = "building", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  List<Facility> facilities;

  // 교통수단
  @OneToMany(mappedBy = "building", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  List<Transportation> transportations;

  @OneToMany(mappedBy = "building", orphanRemoval = true, cascade = CascadeType.REMOVE)
  List<Unit> units;

  @ManyToOne()
  @JoinColumn(name = "member_id")
  Member member;
}