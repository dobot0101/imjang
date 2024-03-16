package com.dobot.imjang.config;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.dobot.imjang.config.CoordinateUtils.Coordinates;
import com.dobot.imjang.domain.building.Building;
import com.dobot.imjang.domain.building.BuildingRepository;

@Service
public class BuildingTestDataInitService {
    private final BuildingRepository buildingRepository;

    public BuildingTestDataInitService(BuildingRepository buildingRepository) {
        this.buildingRepository = buildingRepository;
    }

    public void initBuildingData(int num) {
        List<Building> allBuildingList = buildingRepository.findAll();
        if (allBuildingList.isEmpty()) {
            List<Building> buildingList = new ArrayList<>();
            for (int i = 0; i < num; i++) {
                Coordinates coordinates = CoordinateUtils.getRandomCoordinates();
                Building building = Building.builder()
                        .id(UUID.randomUUID())
                        .latitude(coordinates.latitude())
                        .longitude(coordinates.longitude())
                        .name("아파트 단지 이름")
                        .address("아파트 단지 주소")
                        .build();
                buildingList.add(building);
            }

            if (!buildingList.isEmpty()) {
                buildingRepository.saveAll(buildingList);
            }
        }
    }
}
