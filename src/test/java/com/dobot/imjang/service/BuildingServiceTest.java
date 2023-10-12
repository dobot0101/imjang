package com.dobot.imjang.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dobot.imjang.domain.building.dtos.BuildingRequest;
import com.dobot.imjang.domain.building.entities.Building;
import com.dobot.imjang.domain.building.services.BuildingService;
import com.dobot.imjang.domain.common.exception.CustomException;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
public class BuildingServiceTest {

  @Autowired
  BuildingService buildingService;

  @Test
  void 전체목록조회() {
    List<Building> allBuildings = this.buildingService.getAllBuildings();
    boolean allElementsAreBuildings = allBuildings.stream().allMatch(Building.class::isInstance);
    assertTrue(allElementsAreBuildings, "모든 요소가 Building 클래스의 인스턴트여야 합니다.");
  }

  @Test
  void 저장() {
    BuildingRequest buildingCreateRequest = new BuildingRequest();
    buildingCreateRequest.setAddress("경인로 15길 70-22");
    buildingCreateRequest.setName("테스트 빌딩");
    Building building = buildingService.createBuilding(buildingCreateRequest);

    assertNotNull(building, "빌딩 객체가 생성되어야 합니다.");
    assertEquals(buildingCreateRequest.getName(), building.getName(), "빌딩 이름이 일치해야 합니다.");
    assertEquals(buildingCreateRequest.getAddress(), building.getAddress(), "빌딩 주소가 일치해야 합니다.");
  }

  @Test
  void 삭제() {
    BuildingRequest buildingCreateRequest = new BuildingRequest();
    buildingCreateRequest.setAddress("경인로 15길 70-22");
    buildingCreateRequest.setName("테스트 빌딩");
    Building building = buildingService.createBuilding(buildingCreateRequest);
    buildingService.deleteBuilding(building.getId());

    assertThrows(CustomException.class, () -> {
      buildingService.getBuildingById(building.getId());
    });
  }
}
