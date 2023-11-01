package com.dobot.imjang.domain.building.service;

import java.util.List;
import java.util.UUID;

import com.dobot.imjang.domain.building.dto.BuildingCreateOrUpdateRequestDto;
import com.dobot.imjang.domain.building.entity.Building;

public interface BuildingService {
    public List<Building> getAllBuildings();

    public List<Building> getBuildingsByMemberId(UUID memberId);

    public Building getBuildingById(UUID id);

    public Building createBuilding(BuildingCreateOrUpdateRequestDto buildingRequest);

    public Building updateBuilding(UUID id, BuildingCreateOrUpdateRequestDto buildingRequest);

    public void deleteBuilding(UUID id);
}
