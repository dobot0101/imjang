package com.dobot.imjang.domain.unit;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.dobot.imjang.domain.building.Building;
import com.dobot.imjang.domain.building.BuildingRepository;
import com.dobot.imjang.domain.common.exception.CustomException;
import com.dobot.imjang.domain.common.exception.ErrorCode;
import com.dobot.imjang.domain.upload.UploadResultRepository;

import jakarta.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UnitService {
    private static final String UNIT_ID_SHOULD_NOT_BE_NULL = "Unit id should not be null.";
    private static final String BUILDING_ID_SHOULD_NOT_BE_NULL = "Building id should not be null.";
    private final UnitRepository unitRepository;
    private final BuildingRepository buildingRepository;
    private final UploadResultRepository uploadResultRepository;

    public List<Unit> getAllUnits() {
        return this.unitRepository.findAll();
    }

    public Unit createUnit(UnitCreateOrUpdateDto dto, UUID buildingId) {
        if (Objects.isNull(buildingId)) {
            throw new CustomException(ErrorCode.INVALID_INPUT, BUILDING_ID_SHOULD_NOT_BE_NULL);
        }
        Building building = this.buildingRepository.findById(buildingId)
                .orElseThrow(() -> new CustomException(ErrorCode.BUILDING_NOT_FOUND));

        Unit unit = new Unit();
        unit.setId(UUID.randomUUID());
        unit.setBuilding(building);
        addUnitProperties(dto, unit);

        return this.unitRepository.save(unit);
    }

    @Transactional
    public Unit updateUnit(UUID id, UnitCreateOrUpdateDto dto) {
        if (Objects.isNull(id)) {
            throw new CustomException(ErrorCode.INVALID_INPUT, UNIT_ID_SHOULD_NOT_BE_NULL);
        }
        Unit unit = this.unitRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.UNIT_NOT_FOUND));
        Unit updatingUnit = this.addUnitProperties(dto, unit);
        return this.unitRepository.save(updatingUnit);
    }

    @Transactional
    public void deleteUnitById(UUID id) {
        if (Objects.isNull(id)) {
            throw new CustomException(ErrorCode.INVALID_INPUT, UNIT_ID_SHOULD_NOT_BE_NULL);
        }
        this.unitRepository.deleteById(id);
    }

    public Unit getUnitById(UUID id) {
        return unitRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.UNIT_NOT_FOUND));
    }

    private Unit addUnitProperties(UnitCreateOrUpdateDto dto, Unit unit) {
        unit.setArea(dto.getArea());
        unit.setBuildingNumber(dto.getBuildingNumber());
        unit.setCondensationMoldLevel(dto.getCondensationMoldLevel());
        unit.setDirection(dto.getDirection());
        unit.setLeakStatus(dto.getLeakStatus());
        unit.setMemo(dto.getMemo());
        unit.setNoiseLevel(dto.getNoiseLevel());
        unit.setRoomNumber(dto.getRoomNumber());
        unit.setVentilation(dto.getVentilation());
        unit.setViewQuality(dto.getViewQuality());
        unit.setWaterPressure(dto.getWaterPressure());
        unit.setMonthlyPrice(dto.getMonthlyPrice());
        unit.setJeonsePrice(dto.getJeonsePrice());
        unit.setSalePrice(dto.getSalePrice());
        unit.setDeposit(dto.getDeposit());

        if (!dto.getUploadedFileIds().isEmpty()) {
            var uploadedFileIds = dto.getUploadedFileIds().stream().map(UUID::fromString).toList();
            var uploadResults = uploadResultRepository.findAllById(uploadedFileIds);
            List<UnitImage> newUnitImages = uploadResults.stream()
                    .map(uploadedFile -> UnitImage.builder().id(UUID.randomUUID()).unit(unit)
                            .uploadResult(uploadedFile)
                            .build())
                    .toList();

            List<UnitImage> filteredOriginalUnitImages = unit.getImages().stream()
                    .filter(image -> dto.getUploadedFileIds().contains(image.getUploadResult().getId().toString()))
                    .toList();

            filteredOriginalUnitImages.addAll(newUnitImages);
            unit.getImages().clear();
            unit.getImages().addAll(filteredOriginalUnitImages);
        } else {
            unit.getImages().clear();
        }

        return unit;
    }
}
