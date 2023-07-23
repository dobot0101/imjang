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
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;

import com.dobot.imjang.enums.ElevatorStatus;
import com.dobot.imjang.enums.EntranceStructure;
import com.dobot.imjang.enums.ParkingSpace;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Building {
  @Id
  UUID id;

  @Column(nullable = false)
  private double latitude;

  @Column(nullable = false)
  private double longitude;

  @Column(nullable = false)
  String name; // 건물 이름 (예: 아파트 이름)

  @Column(nullable = false)
  String address; // 건물 주소

  @CreationTimestamp
  @Column(nullable = false, updatable = false)
  LocalDateTime createdDate;

  @OneToMany(mappedBy = "building", cascade = CascadeType.ALL, orphanRemoval = true)
  List<Unit> units;

  @Enumerated(EnumType.STRING)
  ElevatorStatus elevatorStatus;

  @Enumerated(EnumType.STRING)
  EntranceStructure entranceStructure;

  @Enumerated(EnumType.STRING)
  ParkingSpace parkingSpace;

  @OneToMany(mappedBy = "building", cascade = CascadeType.ALL, orphanRemoval = true)
  List<SchoolDistrict> schoolDistricts;

  @OneToMany(mappedBy = "building", cascade = CascadeType.ALL, orphanRemoval = true)
  List<Facility> facilities;

  @OneToMany(mappedBy = "building", cascade = CascadeType.ALL, orphanRemoval = true)
  List<Transportation> transportations;

}