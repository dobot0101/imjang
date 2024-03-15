package com.dobot.imjang.domain.building;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import com.dobot.imjang.config.CoordinateUtils;
import com.dobot.imjang.config.CoordinateUtils.Coordinates;
import java.util.Comparator;

@SpringBootTest
public class CustomBuildingRepositoryImplTest {
    @Autowired
    @Qualifier("buildingRepository")
    private BuildingRepository buildingRepository;

    @Autowired
    @Qualifier("customBuildingRepositoryImpl")
    private CustomBuildingRepositoryImpl customBuildingRepositoryImpl;

    @BeforeEach
    void setUp() {
        List<Building> buildingList = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
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

    @Test
    void findWithCursorPagination() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);

        List<Building> buildingList = buildingRepository.findAll();
        // buildingList.sort((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()));
        buildingList.sort(Comparator.comparing(Building::getCreatedAt).reversed());
        var building = buildingList.get(0);
        var cursor = building.getId();
        LocalDateTime createdAt = building.getCreatedAt();

        Slice<Building> result = customBuildingRepositoryImpl.findWithCursorPagination(cursor, createdAt, pageable);
        assertEquals(pageable.getPageSize(), result.getContent().size(), "The result size should match the page size");
    }

    @Test
    void findWithOffsetPagination() {
        Pageable pageable = PageRequest.of(0, 10);

        List<Building> result = customBuildingRepositoryImpl.findWithOffsetPagination(pageable);
        assertEquals(pageable.getPageSize(), result.size(), "The result size should match the page size");
    }
}