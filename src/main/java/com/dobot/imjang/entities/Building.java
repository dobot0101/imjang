package com.dobot.imjang.entities;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.dobot.imjang.enums.ElevatorStatus;
import com.dobot.imjang.enums.EntranceStructure;
import com.dobot.imjang.enums.ParkingSpace;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(indexes = {
    @Index(columnList = "latitude, longitude", unique = true)
})
public class Building {
  @Id
  UUID id;

  // 좌표(위도)
  @Column(nullable = false)
  private double latitude;

  // 좌표(경도)
  @Column(nullable = false)
  private double longitude;

  // 건물 이름 (예: 아파트 단지 이름)
  @Column(nullable = false)
  String name;

  // 건물 주소
  @Column(nullable = false)
  String address;

  // @OneToMany(mappedBy = "building", orphanRemoval = true)
  // List<Unit> units;

  // 엘리베이터(있음, 없음, 지하주차장 연결)
  @Enumerated(EnumType.STRING)
  ElevatorStatus elevatorStatus;

  // 현관구조(계단식, 복도식)
  @Enumerated(EnumType.STRING)
  EntranceStructure entranceStructure;

  // 주차공간(많음, 적음, 보통)
  @Enumerated(EnumType.STRING)
  ParkingSpace parkingSpace;

  // 학군
  @OneToMany(mappedBy = "building", cascade = CascadeType.ALL, orphanRemoval = true)
  List<SchoolDistrict> schoolDistricts;

  // 주변시설
  @OneToMany(mappedBy = "building", cascade = CascadeType.ALL, orphanRemoval = true)
  List<Facility> facilities;

  // 교통수단
  @OneToMany(mappedBy = "building", cascade = CascadeType.ALL, orphanRemoval = true)
  List<Transportation> transportations;

  @CreationTimestamp
  @Column(nullable = true, updatable = false)
  LocalDateTime createdAt;

  @CreationTimestamp
  LocalDateTime modifiedAt;
}