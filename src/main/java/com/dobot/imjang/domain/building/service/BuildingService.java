package com.dobot.imjang.domain.building.service;

import java.util.List;
import java.util.UUID;

import com.dobot.imjang.domain.building.dto.BuildingCreateOrUpdateRequestDto;
import com.dobot.imjang.domain.building.entity.Building;
import com.dobot.imjang.domain.member.entity.Member;

public interface BuildingService {
    public List<Building> getAllBuildings();

    public List<Building> getBuildingsByMember(Member member);

    public Building getBuildingById(UUID id);

    public Building createBuilding(BuildingCreateOrUpdateRequestDto buildingRequest);

    public Building updateBuilding(UUID id, BuildingCreateOrUpdateRequestDto buildingRequest);

    public void deleteBuilding(UUID id);
}
