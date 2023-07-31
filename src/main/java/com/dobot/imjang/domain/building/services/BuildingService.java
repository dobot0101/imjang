package com.dobot.imjang.domain.building.services;

import java.util.List;
import java.util.UUID;

import com.dobot.imjang.domain.building.dtos.BuildingRequest;
import com.dobot.imjang.domain.building.entities.Building;

public interface BuildingService {
    public List<Building> getAllBuildings();

    public Building getBuildingById(UUID id);

    public Building createBuilding(BuildingRequest buildingRequest);

    public Building updateBuilding(UUID id, BuildingRequest buildingRequest);

    public void deleteBuilding(UUID id);
}
