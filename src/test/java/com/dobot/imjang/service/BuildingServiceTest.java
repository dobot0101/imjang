package com.dobot.imjang.service;

import com.dobot.imjang.domain.building.Building;
import com.dobot.imjang.domain.building.BuildingCreateOrUpdateRequestDto;
import com.dobot.imjang.domain.building.BuildingRepository;
import com.dobot.imjang.domain.building.CustomBuildingRepository;
import com.dobot.imjang.domain.building.BuildingService;
import com.dobot.imjang.domain.common.exception.CustomException;
import com.dobot.imjang.domain.member.Member;
import com.dobot.imjang.domain.member.MemberRepository;
import com.dobot.imjang.domain.member.MemberService;
import com.dobot.imjang.domain.member.SignUpRequestDto;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestInstance(Lifecycle.PER_CLASS)
public class BuildingServiceTest {

  @Autowired
  private BuildingService buildingService;

  @Autowired
  private MemberService memberService;

  @Autowired
  private MemberRepository memberRepository;

  @Autowired
  private BuildingRepository buildingRepository;

  private Member member;
  private Building building;

  @BeforeAll
  void 테스트데이터_생성() throws Exception {
    SignUpRequestDto dto = SignUpRequestDto.builder().confirmPassword("test").password("test").email("test@test.co.kr")
        .name("tester").build();
    member = memberService.signUp(dto);

    BuildingCreateOrUpdateRequestDto buildingCreateRequestDto = BuildingCreateOrUpdateRequestDto.builder()
        .latitude(99.999999)
        .longitude(99.999999)
        .name("테스트 건물명").address("테스트 건물 주소")
        .build();
    building = buildingService.createBuilding(buildingCreateRequestDto, member);
  }

  @AfterAll
  void 테스트데이터_삭제() {
    buildingRepository.deleteById(building.getId());
    memberRepository.delete(member);
  }

  @Test
  void 전체_건물정보_목록조회() {
    List<Building> allBuildings = this.buildingService.getAllBuildings();
    boolean allElementsAreBuildings = allBuildings.stream().allMatch(Building.class::isInstance);
    assertTrue(allElementsAreBuildings, "모든 요소가 Building 클래스의 인스턴트여야 합니다.");
  }

  @Test
  void 건물정보_저장() {
    BuildingCreateOrUpdateRequestDto buildingCreateOrUpdateDto = BuildingCreateOrUpdateRequestDto.builder()
        .latitude(9.9999).longitude(9.9999)
        .address("테스트 건물 주소").name("테스트 빌딩").build();
    Building building = buildingService.createBuilding(buildingCreateOrUpdateDto, member);

    assertNotNull(building, "빌딩 객체가 생성되어야 합니다.");
    assertEquals(buildingCreateOrUpdateDto.getName(), building.getName(), "빌딩 이름이 일치해야 합니다.");
    assertEquals(buildingCreateOrUpdateDto.getAddress(), building.getAddress(), "빌딩 주소가 일치해야 합니다.");
  }

  @Test
  void 건물정보_삭제() {
    BuildingCreateOrUpdateRequestDto buildingCreateOrUpdateDto = BuildingCreateOrUpdateRequestDto.builder()
        .latitude(9.9999).longitude(9.9999)
        .address("테스트 건물 주소")
        .name("테스트 빌딩").build();
    Building building = buildingService.createBuilding(buildingCreateOrUpdateDto, member);
    buildingService.deleteBuilding(building.getId());

    assertThrows(CustomException.class, () -> {
      buildingService.getBuildingById(building.getId());
    });
  }

  @Test
  void 건물정보_수정() {
    BuildingCreateOrUpdateRequestDto createDto = BuildingCreateOrUpdateRequestDto.builder()
        .latitude(9.9999).longitude(9.9999)
        .address("테스트 건물 주소")
        .name("테스트 빌딩").build();
    Building building = buildingService.createBuilding(createDto, member);

    BuildingCreateOrUpdateRequestDto updateDto = BuildingCreateOrUpdateRequestDto.builder().name("수정된 테스트 빌딩").build();
    Building updatedBuilding = buildingService.updateBuilding(building.getId(), updateDto);

    assertEquals(updatedBuilding.getName(), "수정된 테스트 빌딩");
  }
}
