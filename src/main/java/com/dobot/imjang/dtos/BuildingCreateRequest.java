package com.dobot.imjang.dtos;

import java.util.List;

import com.dobot.imjang.enums.ElevatorStatus;
import com.dobot.imjang.enums.EntranceStructure;
import com.dobot.imjang.enums.ParkingSpace;
import com.dobot.imjang.enums.SchoolType;

public class BuildingCreateRequest {
  String name;
  String address;
  ElevatorStatus elevatorStatus;
  EntranceStructure entranceStructure;
  ParkingSpace parkingSpace;
  List<SchoolType> schoolDistricts;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public void setElevatorStatus(ElevatorStatus elevatorStatus) {
    this.elevatorStatus = elevatorStatus;
  }

  public ElevatorStatus getElevatorStatus() {
    return elevatorStatus;
  }

}
