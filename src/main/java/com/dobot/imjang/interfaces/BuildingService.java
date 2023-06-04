package com.dobot.imjang.interfaces;

import java.util.List;
import java.util.UUID;

import com.dobot.imjang.dtos.BuildingCreateRequest;
import com.dobot.imjang.dtos.BuildingUpdateRequest;
import com.dobot.imjang.entities.Building;

public interface BuildingService {
  List<Building> getAllBuildings();

  Building getBuildingById(UUID id);

  Building createBuilding(BuildingCreateRequest buildingCreateRequest);

  Building updateBuilding(UUID id, BuildingUpdateRequest buildingUpdateRequest);

  void deleteBuilding(UUID id);
}
