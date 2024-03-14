package com.dobot.imjang.config;

import com.dobot.imjang.domain.building.Building;
import com.dobot.imjang.domain.building.BuildingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class BuildingTestDataInitService {
    private final BuildingRepository buildingRepository;

    @Autowired
    public BuildingTestDataInitService(BuildingRepository buildingRepository) {
        this.buildingRepository = buildingRepository;
    }

    public void initBuildingData() {
        List<Building> allBuildingList = buildingRepository.findAll();
        if (allBuildingList.isEmpty()) {
            List<Building> buildingList = new ArrayList<>();
            for (int i = 0; i < 10000; i++) {
                Coordinates coordinates = getRandomCoordinates();
                Building building = Building.builder()
                        .id(UUID.randomUUID())
                        .latitude(coordinates.latitude)
                        .longitude(coordinates.longitude)
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

    private record Coordinates(double latitude, double longitude) {
    }

    private Coordinates getRandomCoordinates() {
        // Create a Random object
        Random random = new Random();

        // Generate random latitude and longitude
        double minLat = -90.0;
        double maxLat = 90.0;
        double randomLat = minLat + (maxLat - minLat) * random.nextDouble();

        double minLon = -180.0;
        double maxLon = 180.0;
        double randomLon = minLon + (maxLon - minLon) * random.nextDouble();

        return new Coordinates(randomLat, randomLon);
    }
}
