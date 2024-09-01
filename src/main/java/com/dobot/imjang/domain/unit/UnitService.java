package com.dobot.imjang.domain.unit;

import com.dobot.imjang.domain.building.Building;
import com.dobot.imjang.domain.building.BuildingRepository;
import com.dobot.imjang.domain.common.exception.CustomException;
import com.dobot.imjang.domain.common.exception.ErrorCode;
import com.dobot.imjang.domain.upload.UploadResult;
import com.dobot.imjang.domain.upload.UploadResultRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

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
        validateBuildingId(buildingId);
        Building building = this.buildingRepository.findById(buildingId)
                .orElseThrow(() -> new CustomException(ErrorCode.BUILDING_NOT_FOUND));

        Unit unit = new Unit();
        unit.setId(UUID.randomUUID());
        unit.setBuilding(building);
        unit.setProperties(dto);
        addUnitImages(dto, unit);

        return this.unitRepository.save(unit);
    }

    private void validateBuildingId(UUID buildingId) {
        if (Objects.isNull(buildingId)) {
            throw new CustomException(ErrorCode.INVALID_INPUT, BUILDING_ID_SHOULD_NOT_BE_NULL);
        }
    }

    @Transactional
    public Unit updateUnit(UUID id, UnitCreateOrUpdateDto dto) {
        validateUnitId(id);
        Unit unit = this.unitRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.UNIT_NOT_FOUND));
        unit.setProperties(dto);
        addUnitImages(dto, unit);
        return this.unitRepository.save(unit);
    }

    private void validateUnitId(UUID id) {
        if (Objects.isNull(id)) {
            throw new CustomException(ErrorCode.INVALID_INPUT, UNIT_ID_SHOULD_NOT_BE_NULL);
        }
    }

    @Transactional
    public void deleteUnitById(UUID id) {
        validateUnitId(id);
        this.getUnitById(id);
        this.unitRepository.deleteById(id);
    }

    public Unit getUnitById(UUID id) {
        return unitRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.UNIT_NOT_FOUND));
    }

    private void addUnitImages(UnitCreateOrUpdateDto dto, Unit unit) {
        if (!dto.getUploadedFileIds().isEmpty()) {
            List<UnitImage> newUnitImages = new ArrayList<>();
            for (var fileId : dto.getUploadedFileIds()) {
                unit.getImages().stream().filter(image -> image.getUploadResult().getId().equals(fileId)).findFirst()
                        .ifPresentOrElse(newUnitImages::add, () -> {
                            UploadResult uploadResult = uploadResultRepository.findById(fileId)
                                    .orElseThrow(() -> new CustomException(ErrorCode.UPLOAD_RESULT_NOT_FOUND));
                            UnitImage unitImage = UnitImage.builder().id(UUID.randomUUID()).unit(unit)
                                    .uploadResult(uploadResult).build();
                            newUnitImages.add(unitImage);
                        });
            }

            unit.getImages().clear();
            unit.getImages().addAll(newUnitImages);
        }
    }
}
