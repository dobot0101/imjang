package com.dobot.imjang.domain.building;

import com.dobot.imjang.config.CoordinateUtils;
import com.dobot.imjang.config.CoordinateUtils.Coordinates;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class BuildingPagingTest {
    @Autowired
    private BuildingRepository buildingRepository;
    private final int MAX_COUNT = 11;

    @BeforeEach
    void setUp() {
        List<Building> buildingList = new ArrayList<>();
        for (int i = 0; i < MAX_COUNT; i++) {
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
    @DisplayName("Spring Data JPA가 자동으로 생성해주는 페이징 테스트")
    void findPageBy() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Building> page = this.buildingRepository.findPageBy(pageRequest);
        System.out.println(page);
        assertEquals(pageRequest.getPageSize(), page.getContent().size());
        assertEquals(page.getTotalElements(), MAX_COUNT);
    }

    @Test
    @DisplayName("커서 페이지네이션 테스트")
    void findWithCursorPagination() {
        Pageable pageable = PageRequest.of(0, 10);

        List<Building> buildingList = buildingRepository.findAll();
        buildingList.sort(Comparator.comparing(Building::getCreatedAt).thenComparing(Building::getId
        ).reversed());

        var building = buildingList.get(0);
        var cursor = building.getId();
        LocalDateTime createdAt = building.getCreatedAt();

        Slice<Building> result = buildingRepository.findWithCursorPagination(cursor, createdAt, pageable);
        assertEquals(pageable.getPageSize(), result.getContent().size(), "The result size should match the page size");
    }

    @Test
    @DisplayName("오프셋 페이지네이션 테스트")
    void findWithOffsetPagination() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Building> result = buildingRepository.findWithOffsetPagination(pageable);
        assertEquals(pageable.getPageSize(), result.getContent().size(), "The result size should match the page size");
    }

    @AfterEach
    void tearDown() {
        buildingRepository.deleteAll();
    }
}