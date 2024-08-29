package com.dobot.imjang.domain.building;

import com.dobot.imjang.domain.common.exception.CustomException;
import com.dobot.imjang.domain.common.exception.ErrorCode;
import com.dobot.imjang.domain.member.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class BuildingServiceTest {

    @Mock
    private BuildingRepository buildingRepository;

    @InjectMocks
    private BuildingService buildingService;

    private Member member;
    private Building building;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        member = Member.builder().id(UUID.randomUUID()).build();
        building = Building.builder()
                .id(UUID.randomUUID())
                .latitude(37.7749)
                .longitude(-122.4194)
                .member(member)
                .address("Test Address")
                .name("Test Building")
                .build();
    }

    @Test
    @DisplayName("건물정보 조회")
    void getBuildingById() {
        when(buildingRepository.findById(building.getId())).thenReturn(Optional.of(building));
        Building foundBuilding = buildingService.getBuildingById(building.getId());
        assertNotNull(foundBuilding, "빌딩 객체가 조회되어야 합니다.");
        assertEquals(building.getId(), foundBuilding.getId(), "빌딩 ID가 일치해야 합니다.");
    }

    @Test
    @DisplayName("건물정보 조회 - 존재하지 않는 ID")
    void getBuildingById_NotFound() {
        UUID nonExistentId = UUID.randomUUID();
        CustomException exception = assertThrows(CustomException.class, () -> {
            buildingService.getBuildingById(nonExistentId);
        });
        assertEquals(ErrorCode.BUILDING_NOT_FOUND, exception.getErrorCode(), "에러 코드가 BUILDING_NOT_FOUND여야 합니다.");
    }

    @Test
    @DisplayName("건물정보 생성 - 중복된 위치")
    void createBuilding_DuplicateLocation() {
        CreateBuildingDto duplicateLocationDto = CreateBuildingDto.builder()
                .latitude(building.getLatitude())
                .longitude(building.getLongitude())
                .name("중복 위치 테스트 빌딩")
                .address("중복 위치 테스트 주소")
                .build();
        when(buildingRepository.findByLatitudeAndLongitude(duplicateLocationDto.getLatitude(), duplicateLocationDto.getLongitude()))
                .thenReturn(List.of(building));
        CustomException exception = assertThrows(CustomException.class, () -> {
            buildingService.createBuilding(duplicateLocationDto, member);
        });
        assertEquals(ErrorCode.DUPLICATE_LOCATION, exception.getErrorCode(), "에러 코드가 DUPLICATE_LOCATION이어야 합니다.");
    }

    @Test
    @DisplayName("건물정보 수정 - 존재하지 않는 ID")
    void updateBuilding_NotFound() {
        UUID nonExistentId = UUID.randomUUID();
        UpdateBuildingDto updateDto = UpdateBuildingDto.builder().name("수정된 테스트 빌딩").build();
        CustomException exception = assertThrows(CustomException.class, () -> {
            buildingService.updateBuilding(nonExistentId, updateDto);
        });
        assertEquals(ErrorCode.BUILDING_NOT_FOUND, exception.getErrorCode(), "에러 코드가 BUILDING_NOT_FOUND여야 합니다.");
    }

    @Test
    @DisplayName("건물정보 삭제 - 존재하지 않는 ID")
    void deleteBuilding_NotFound() {
        UUID nonExistentId = UUID.randomUUID();
        // 아래와 같이 mocking 하지 않으면 반환 타입의 기본 값을 반환함. 예를들어 Optional을 반환하는 buildingRepository.findById 메소드를 mocking 하지 않으면 Optional.empty()를 반환함
        // 그래서 mocking 하지 않아도 Optional.empty()를 반환하는 경우 CustomException이 발생하는 아래의 테스트는 통과함
        when(buildingRepository.findById(nonExistentId)).thenReturn(Optional.empty());
        CustomException exception = assertThrows(CustomException.class, () -> {
            buildingService.deleteBuilding(nonExistentId);
        });
        assertEquals(ErrorCode.BUILDING_NOT_FOUND, exception.getErrorCode(), "에러 코드가 BUILDING_NOT_FOUND여야 합니다.");
    }
}



