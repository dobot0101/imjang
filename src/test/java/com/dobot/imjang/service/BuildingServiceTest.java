package com.dobot.imjang.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dobot.imjang.dtos.CreateBuildingRequest;
import com.dobot.imjang.entities.Building;

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
    CreateBuildingRequest buildingCreateRequest = new CreateBuildingRequest();
    buildingCreateRequest.setAddress("경인로 15길 70-22");
    buildingCreateRequest.setName("테스트 빌딩");
    Building building = buildingService.createBuilding(buildingCreateRequest);

    assertNotNull(building, "빌딩 객체가 생성되어야 합니다.");
    assertEquals(buildingCreateRequest.getName(), building.getName(), "빌딩 이름이 일치해야 합니다.");
    assertEquals(buildingCreateRequest.getAddress(), building.getAddress(), "빌딩 주소가 일치해야 합니다.");
  }
}
