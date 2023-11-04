package com.dobot.imjang.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.dobot.imjang.domain.building.dto.BuildingCreateOrUpdateRequestDto;
import com.dobot.imjang.domain.building.entity.Building;
import com.dobot.imjang.domain.building.repository.BuildingRepository;
import com.dobot.imjang.domain.building.service.BuildingService;
import com.dobot.imjang.domain.common.exception.CustomException;
import com.dobot.imjang.domain.member.dto.SignUpRequestDto;
import com.dobot.imjang.domain.member.entity.Member;
import com.dobot.imjang.domain.member.repository.MemberRepository;
import com.dobot.imjang.domain.member.service.MemberService;
import com.dobot.imjang.domain.unit.dto.UnitCreateOrUpdateDto;
import com.dobot.imjang.domain.unit.entity.Unit;
import com.dobot.imjang.domain.unit.service.UnitService;

@SpringBootTest
@Transactional
@TestInstance(Lifecycle.PER_CLASS)
public class UnitServiceTest {
  @Autowired
  private UnitService unitService;

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
  public void 세대정보_조회() {
    UnitCreateOrUpdateDto dto = UnitCreateOrUpdateDto.builder().memo("테스트 메모").build();
    Unit unit = unitService.createUnit(dto, building.getId());

    Unit foundUnit = unitService.getUnitById(unit.getId());
    assertEquals(foundUnit.getId(), unit.getId());
  }

  @Test
  public void 세대정보_생성() {
    UnitCreateOrUpdateDto dto = UnitCreateOrUpdateDto.builder().memo("테스트 메모").build();
    Unit unit = unitService.createUnit(dto, building.getId());
    assertEquals(unit.getMemo(), "테스트 메모");
  }

  @Test
  public void 세대정보_수정() {
    UnitCreateOrUpdateDto createDto = UnitCreateOrUpdateDto.builder().memo("테스트 메모").build();
    Unit unit = unitService.createUnit(createDto, building.getId());

    UnitCreateOrUpdateDto updateDto = UnitCreateOrUpdateDto.builder().memo("수정된 테스트 메모").build();
    Unit updatedUnit = unitService.updateUnit(unit.getId(), updateDto);

    assertEquals(updatedUnit.getMemo(), "수정된 테스트 메모");
  }

  @Test
  public void 세대정보_삭제() {
    UnitCreateOrUpdateDto createDto = UnitCreateOrUpdateDto.builder().memo("테스트 메모").build();
    Unit unit = unitService.createUnit(createDto, building.getId());

    unitService.deleteUnitById(unit.getId());

    assertThrows(CustomException.class, () -> {
      unitService.getUnitById(unit.getId());
    });
  }
}
