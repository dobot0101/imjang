package com.dobot.imjang.entities;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.dobot.imjang.enums.ElevatorStatus;
import com.dobot.imjang.enums.EntranceStructure;
import com.dobot.imjang.enums.ParkingSpace;

@Entity
public class Building {
  @Id
  @Type(type = "uuid-char")
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
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
  List<SchoolDistrict> schoolDistrict;

  @OneToMany(mappedBy = "building", cascade = CascadeType.ALL, orphanRemoval = true)
  List<Facility> facilities;

  @OneToMany(mappedBy = "building", cascade = CascadeType.ALL, orphanRemoval = true)
  List<Transportation> transportations;

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

  public UUID getId() {
    return id;
  }

  public String getAddress() {
    return address;
  }

  public LocalDateTime getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(LocalDateTime createdDate) {
    this.createdDate = createdDate;
  }

  public void setUnits(List<Unit> units) {
    this.units = units;
  }

  public void setElevatorStatus(ElevatorStatus elevatorStatus) {
    this.elevatorStatus = elevatorStatus;
  }

  public void setEntranceStructure(EntranceStructure entranceStructure) {
    this.entranceStructure = entranceStructure;
  }

  public void setParkingSpace(ParkingSpace parkingSpace) {
    this.parkingSpace = parkingSpace;
  }

  public void setFacilities(List<Facility> facilities) {
    this.facilities = facilities;
  }

  public void setTransportations(List<Transportation> transportations) {
    this.transportations = transportations;
  }

  public double getLatitude() {
    return latitude;
  }

  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  public List<Unit> getUnits() {
    return units;
  }

  public ElevatorStatus getElevatorStatus() {
    return elevatorStatus;
  }

  public EntranceStructure getEntranceStructure() {
    return entranceStructure;
  }

  public ParkingSpace getParkingSpace() {
    return parkingSpace;
  }

  public List<SchoolDistrict> getSchoolDistrict() {
    return schoolDistrict;
  }

  public void setSchoolDistrict(List<SchoolDistrict> schoolDistrict) {
    this.schoolDistrict = schoolDistrict;
  }

  public List<Facility> getFacilities() {
    return facilities;
  }

  public List<Transportation> getTransportations() {
    return transportations;
  }

}