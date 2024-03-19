package com.dobot.imjang.domain.building;

import java.util.List;

import com.dobot.imjang.domain.building.enums.ElevatorStatus;
import com.dobot.imjang.domain.building.enums.EntranceStructure;
import com.dobot.imjang.domain.building.enums.FacilityType;
import com.dobot.imjang.domain.building.enums.ParkingSpace;
import com.dobot.imjang.domain.building.enums.SchoolType;
import com.dobot.imjang.domain.building.enums.TransportationType;

public interface IBuildingDto {

  double getLatitude();

  double getLongitude();

  String getName();

  String getAddress();

  ElevatorStatus getElevatorStatus();

  EntranceStructure getEntranceStructure();

  ParkingSpace getParkingSpace();

  List<SchoolType> getSchoolTypes();

  List<FacilityType> getFacilityTypes();

  List<TransportationType> getTransportationTypes();

}