package com.dobot.imjang.domain.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.dobot.imjang.domain.building.Building;
import com.dobot.imjang.domain.building.BuildingRepository;
import com.dobot.imjang.domain.building.BuildingService;
import com.dobot.imjang.domain.building.CreateBuildingDto;
import com.dobot.imjang.domain.common.exception.CustomException;
import com.dobot.imjang.domain.member.Member;
import com.dobot.imjang.domain.member.MemberRepository;
import com.dobot.imjang.domain.member.MemberService;
import com.dobot.imjang.domain.member.SignUpRequestDto;

@SpringBootTest
@Transactional
@TestInstance(Lifecycle.PER_CLASS)
public class UnitServiceIntegrationTest {
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
    void createTestData() throws Exception {
        SignUpRequestDto dto = SignUpRequestDto.builder().confirmPassword("test").password("test")
                .email("test@test.co.kr")
                .name("tester").build();
        member = memberService.signUp(dto);

        CreateBuildingDto createBuildingDto = CreateBuildingDto.builder()
                .latitude(99.999999)
                .longitude(99.999999)
                .name("테스트 건물명").address("테스트 건물 주소")
                .build();
        building = buildingService.createBuilding(createBuildingDto, member);
    }

    @AfterAll
    void deleteTestData() {
        buildingRepository.deleteById(building.getId());
        memberRepository.delete(member);
    }

    @Test
    @DisplayName("세대정보 조회")
    public void findUnit() {
        UnitCreateOrUpdateDto dto = UnitCreateOrUpdateDto.builder().memo("테스트 메모").build();
        Unit unit = unitService.createUnit(dto, building.getId());

        Unit foundUnit = unitService.getUnitById(unit.getId());
        assertEquals(foundUnit.getId(), unit.getId());
    }

    @Test
    @DisplayName("세대정보 생성")
    public void createUnit() {
        UnitCreateOrUpdateDto dto = UnitCreateOrUpdateDto.builder().memo("테스트 메모").build();
        Unit unit = unitService.createUnit(dto, building.getId());
        assertEquals(unit.getMemo(), "테스트 메모");
    }

    @Test
    @DisplayName("세대정보 수정")
    public void updateUnit() {
        UnitCreateOrUpdateDto createDto = UnitCreateOrUpdateDto.builder().memo("테스트 메모").build();
        Unit unit = unitService.createUnit(createDto, building.getId());

        UnitCreateOrUpdateDto updateDto = UnitCreateOrUpdateDto.builder().memo("수정된 테스트 메모").build();
        Unit updatedUnit = unitService.updateUnit(unit.getId(), updateDto);

        assertEquals(updatedUnit.getMemo(), "수정된 테스트 메모");
    }

    @Test
    @DisplayName("세대정보 삭제")
    public void deleteUnit() {
        UnitCreateOrUpdateDto createDto = UnitCreateOrUpdateDto.builder().memo("테스트 메모").build();
        Unit unit = unitService.createUnit(createDto, building.getId());

        unitService.deleteUnitById(unit.getId());

        assertThrows(CustomException.class, () -> {
            unitService.getUnitById(unit.getId());
        });
    }
}
