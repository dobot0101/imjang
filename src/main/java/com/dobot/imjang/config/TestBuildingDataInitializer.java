package com.dobot.imjang.config;

import com.dobot.imjang.domain.building.Building;
import com.dobot.imjang.domain.building.BuildingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Component
public class TestBuildingDataInitializer implements ApplicationRunner {
    private final BuildingRepository buildingRepository;

    @Autowired
    public TestBuildingDataInitializer(BuildingRepository buildingRepository) {
        this.buildingRepository = buildingRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        initBuildingData();
    }


    private record Coordinates(double latitude, double longitude) {
    }

    protected void initBuildingData() {
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
