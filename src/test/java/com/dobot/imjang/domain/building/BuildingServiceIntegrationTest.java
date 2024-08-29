package com.dobot.imjang.domain.building;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.dobot.imjang.domain.common.exception.CustomException;
import com.dobot.imjang.domain.member.Member;
import com.dobot.imjang.domain.member.MemberRepository;
import com.dobot.imjang.domain.member.MemberService;
import com.dobot.imjang.domain.member.SignUpRequestDto;

@SpringBootTest
@Transactional
@TestInstance(Lifecycle.PER_CLASS)
public class BuildingServiceIntegrationTest {

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

    @BeforeEach
    void createTestData() throws Exception {
        SignUpRequestDto dto = SignUpRequestDto.builder().confirmPassword("test").password("test").email("test@test.co.kr")
                .name("tester").build();
        member = memberService.signUp(dto);

        CreateBuildingDto createBuildingDto = CreateBuildingDto.builder()
                .latitude(99.999999)
                .longitude(99.999999)
                .name("테스트 건물명").address("테스트 건물 주소")
                .build();
        building = buildingService.createBuilding(createBuildingDto, member);
    }

    @AfterEach
    void deleteTestData() {
        buildingRepository.deleteById(building.getId());
        memberRepository.delete(member);
    }

    @Test
    @DisplayName("모든 건물 정보 조회")
    void findAllBuildings() {
        List<Building> allBuildings = this.buildingService.getAllBuildings();
        boolean allElementsAreBuildings = allBuildings.stream().allMatch(Building.class::isInstance);
        assertTrue(allElementsAreBuildings, "모든 요소가 Building 클래스의 인스턴트여야 합니다.");
    }

    @Test
    @DisplayName("건물정보 저장")
    void createBuilding() {
        CreateBuildingDto createBuildingDto = CreateBuildingDto.builder()
                .latitude(9.9999).longitude(9.9999)
                .address("테스트 건물 주소").name("테스트 빌딩").build();
        Building building = buildingService.createBuilding(createBuildingDto, member);

        assertNotNull(building, "빌딩 객체가 생성되어야 합니다.");
        assertEquals(createBuildingDto.getName(), building.getName(), "빌딩 이름이 일치해야 합니다.");
        assertEquals(createBuildingDto.getAddress(), building.getAddress(), "빌딩 주소가 일치해야 합니다.");
    }

    @Test
    @DisplayName("건물정보 삭제")
    void deleteBuilding() {
        buildingService.deleteBuilding(building.getId());

        assertThrows(CustomException.class, () -> {
            buildingService.getBuildingById(building.getId());
        });
    }

    @Test
    @DisplayName("건물정보 수정")
    void updateBuilding() {
        UpdateBuildingDto updateDto = UpdateBuildingDto.builder().name("수정된 테스트 빌딩").build();
        Building updatedBuilding = buildingService.updateBuilding(building.getId(), updateDto);

        assertEquals(updatedBuilding.getName(), "수정된 테스트 빌딩");
    }
}
