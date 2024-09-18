package com.dobot.imjang.domain.unit;

import com.dobot.imjang.domain.building.Building;
import com.dobot.imjang.domain.building.BuildingRepository;
import com.dobot.imjang.domain.common.exception.ValidationError;
import com.dobot.imjang.domain.common.exception.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UnitServiceTest {
    @Mock
    private UnitRepository unitRepository;

    @Mock
    private BuildingRepository buildingRepository;

    @InjectMocks
    private UnitService unitService;

    private Building building;
    private Unit unit;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        building = Building.builder()
                .id(UUID.randomUUID())
                .build();
        unit = Unit.builder()
                .id(UUID.randomUUID())
                .building(building)
                .build();
    }

    @Test
    @DisplayName("모든 유닛 조회 - 모든 유닛 반환")
    void getAllUnits_ReturnsAllUnits() {
        List<Unit> units = List.of(new Unit(), new Unit());
        when(unitRepository.findAll()).thenReturn(units);

        List<Unit> result = unitService.getAllUnits();

        assertEquals(units, result);
    }

    @Test
    @DisplayName("유닛 생성 - 유효한 입력, 생성된 유닛 반환")
    void createUnit_ValidInput_ReturnsCreatedUnit() {
        UUID buildingId = UUID.randomUUID();
        var dto = UnitCreateOrUpdateDto.builder().build();

        when(buildingRepository.findById(buildingId)).thenReturn(Optional.of(building));
        when(unitRepository.save(any(Unit.class))).thenReturn(unit);

        Unit result = unitService.createUnit(dto, buildingId);

        assertEquals(unit, result);
    }

    @Test
    @DisplayName("유닛 생성 - 빌딩 ID가 null인 경우 CustomException 발생")
    void createUnit_NullBuildingId_ThrowsCustomException() {
        UnitCreateOrUpdateDto dto = UnitCreateOrUpdateDto.builder().build();

        ValidationError exception = assertThrows(
            ValidationError.class, () -> unitService.createUnit(dto, null));

        assertEquals(ErrorCode.BAD_REQUEST, exception.getErrorCode());
    }

    @Test
    @DisplayName("유닛 생성 - 빌딩을 찾을 수 없는 경우 CustomException 발생")
    void createUnit_BuildingNotFound_ThrowsCustomException() {
        UUID buildingId = UUID.randomUUID();
        UnitCreateOrUpdateDto dto = UnitCreateOrUpdateDto.builder().build();

        when(buildingRepository.findById(buildingId)).thenReturn(Optional.empty());

        ValidationError exception = assertThrows(
            ValidationError.class, () -> unitService.createUnit(dto, buildingId));

        assertEquals(ErrorCode.BUILDING_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    @DisplayName("유닛 업데이트 - 유효한 입력, 업데이트된 유닛 반환")
    void updateUnit_ValidInput_ReturnsUpdatedUnit() {
        UnitCreateOrUpdateDto dto = UnitCreateOrUpdateDto.builder().build();

        UUID unitId = unit.getId();
        when(unitRepository.findById(unitId)).thenReturn(Optional.of(unit));
        when(unitRepository.save(any(Unit.class))).thenReturn(unit);

        Unit result = unitService.updateUnit(unitId, dto);

        assertEquals(unit, result);
    }

    @Test
    @DisplayName("유닛 업데이트 - 유닛 ID가 null인 경우 CustomException 발생")
    void updateUnit_NullUnitId_ThrowsCustomException() {
        UnitCreateOrUpdateDto dto = UnitCreateOrUpdateDto.builder().build();

        ValidationError exception = assertThrows(
            ValidationError.class, () -> unitService.updateUnit(null, dto));

        assertEquals(ErrorCode.BAD_REQUEST, exception.getErrorCode());
    }

    @Test
    @DisplayName("유닛 업데이트 - 유닛을 찾을 수 없는 경우 CustomException 발생")
    void updateUnit_UnitNotFound_ThrowsCustomException() {
        UUID unitId = UUID.randomUUID();
        UnitCreateOrUpdateDto dto = UnitCreateOrUpdateDto.builder().build();

        when(unitRepository.findById(unitId)).thenReturn(Optional.empty());

        ValidationError exception = assertThrows(
            ValidationError.class, () -> unitService.updateUnit(unitId, dto));

        assertEquals(ErrorCode.UNIT_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    @DisplayName("유닛 삭제 - 유효한 ID, 유닛 삭제")
    void deleteUnitById_ValidId_DeletesUnit() {
        UUID unitId = unit.getId();
        when(unitRepository.findById(unitId)).thenReturn(Optional.of(unit));
        unitService.deleteUnitById(unitId);

        verify(unitRepository, times(1)).deleteById(unitId);
    }

    @Test
    @DisplayName("유닛 삭제 - 유닛 ID가 null인 경우 CustomException 발생")
    void deleteUnitById_NullUnitId_ThrowsCustomException() {
        ValidationError exception = assertThrows(ValidationError.class, () -> unitService.deleteUnitById(null));

        assertEquals(ErrorCode.BAD_REQUEST, exception.getErrorCode());
    }

    @Test
    @DisplayName("유닛 조회 - 유효한 ID, 유닛 반환")
    void getUnitById_ValidId_ReturnsUnit() {
        UUID unitId = unit.getId();
        when(unitRepository.findById(unitId)).thenReturn(Optional.of(unit));

        Unit result = unitService.getUnitById(unitId);

        assertEquals(unit, result);
    }

    @Test
    @DisplayName("유닛 조회 - 유닛을 찾을 수 없는 경우 CustomException 발생")
    void getUnitById_UnitNotFound_ThrowsCustomException() {
        UUID unitId = UUID.randomUUID();

        when(unitRepository.findById(unitId)).thenReturn(Optional.empty());

        ValidationError exception = assertThrows(ValidationError.class, () -> unitService.getUnitById(unitId));

        assertEquals(ErrorCode.UNIT_NOT_FOUND, exception.getErrorCode());
    }
}