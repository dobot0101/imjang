package com.dobot.imjang.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
public class Building {
  @Id
  @Type(type = "uuid-char")
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  UUID id;

  @Column(nullable = false)
  String name; // 건물 이름 (예: 아파트 이름)

  @Column(nullable = false)
  String address; // 건물 주소

  @CreationTimestamp
  @Column(nullable = false, updatable = false)
  LocalDateTime createdDate;

  @Enumerated(EnumType.String)
  BuildingType buildingType;

  @OneToMany(mappedBy = "building", cascade = CascadeType.ALL, orphanRemoval = true)
  List<Unit> units;

  @Enumerated(EnumType.String)
  ElevatorStatus elevatorStatus;

  @Enumerated(EnumType.String)
  EntranceStructure entranceStructure;

  @Enumerated(EnumType.String)
  ParkingSpace parkingSpace;

  @OneToMany(mappedBy = "home", cascade = CascadeType.ALL, orphanRemoval = true)
  List<SchoolPresence> schoolPresences;

  @OneToMany(mappedBy = "home", cascade = CascadeType.ALL, orphanRemoval = true)
  List<Facility> facilities;

  @OneToMany(mappedBy = "home", cascade = CascadeType.ALL)
  List<HomeInformationItem> homeInformationItems;

  @OneToMany(mappedBy = "home", cascade = CascadeType.ALL, orphanRemoval = true)
  List<Transportation> transportations;

  @OneToMany(mappedBy = "home", cascade = CascadeType.ALL)
  List<HomeImage> images;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public void setMemo(String memo) {
    this.memo = memo;
  }

  public UUID getId() {
    return id;
  }

  public String getAddress() {
    return address;
  }

  public String getMemo() {
    return memo;
  }

  public List<HomeInformationItem> getHomeInformationItems() {
    return homeInformationItems;
  }

  public void setHomeInformationItems(List<HomeInformationItem> homeInformationItems) {
    this.homeInformationItems = homeInformationItems;
  }

  public List<HomeImage> getImages() {
    return images;
  }

  public void setImages(List<HomeImage> images) {
    this.images = images;
  }

  public LocalDateTime getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(LocalDateTime createdDate) {
    this.createdDate = createdDate;
  }
}

public enum ElevatorStatus {
  AVAILABLE, // 있음
  UNAVAILABLE, // 없음
  PARKING_CONNETED // 주차장 연결
}

public enum EntranceStructure {
  STAIRS, // 계단식
  CORRIDOR // 복도식
}

public enum ParkingSpace {
  ABUNDANT, // 많음
  AVERAGE, // 보통
  LIMITED // 적음
}

public enum BuildingType {

}