package com.dobot.imjang.domain.building;

import java.util.List;
import java.util.UUID;

public interface BuildingService {
    public List<Building> getAllBuildings();

    public List<Building> getBuildingsByMemberId(UUID memberId);

    public Building getBuildingById(UUID id);

    public Building createBuilding(BuildingCreateOrUpdateRequestDto buildingCreateRequestDto, Member member);

    public Building updateBuilding(UUID id, BuildingCreateOrUpdateRequestDto buildingUpdateRequestDto);

    public void deleteBuilding(UUID id);
}
