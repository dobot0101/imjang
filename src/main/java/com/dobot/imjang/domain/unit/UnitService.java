package com.dobot.imjang.domain.unit;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.dobot.imjang.domain.building.Building;
import com.dobot.imjang.domain.building.BuildingRepository;
import com.dobot.imjang.domain.common.exception.CustomException;
import com.dobot.imjang.domain.common.exception.ErrorCode;
import com.dobot.imjang.domain.upload.UploadResult;
import com.dobot.imjang.domain.upload.UploadResultRepository;

@Service
public class UnitService {
    private final UnitRepository unitRepository;
    private final BuildingRepository buildingRepository;
    private final UploadResultRepository uploadResultRepository;

    public UnitService(UnitRepository unitRepository, BuildingRepository buildingRepository,
            UploadResultRepository uploadResultRepository) {
        this.unitRepository = unitRepository;
        this.buildingRepository = buildingRepository;
        this.uploadResultRepository = uploadResultRepository;
    }

    public List<Unit> getAllUnits() {
        return this.unitRepository.findAll();
    }

    public Unit createUnit(UnitCreateOrUpdateDto dto, UUID buildingId) {
        Building building = this.buildingRepository.findById(buildingId)
                .orElseThrow(() -> new CustomException(ErrorCode.BUILDING_NOT_FOUND));

        Unit unit = new Unit();
        unit.setId(UUID.randomUUID());
        unit.setBuilding(building);
        addUnitProperties(dto, unit);

        return this.unitRepository.save(unit);
    }

    public Unit updateUnit(UUID id, UnitCreateOrUpdateDto dto) {
        Unit unit = this.unitRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.UNIT_NOT_FOUND));
        Unit updatingUnit = this.addUnitProperties(dto, unit);
        return this.unitRepository.save(updatingUnit);
    }

    public void deleteUnitById(UUID id) {
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
            List<UnitImage> newUnitImages = dto.getUploadedFileIds().stream().map(uploadedFileId -> {
                UploadResult uploadResult = uploadResultRepository.findById(UUID.fromString(uploadedFileId))
                        .orElseThrow(() -> new CustomException(ErrorCode.UPLOAD_RESULT_NOT_FOUND));

                return UnitImage.builder().id(UUID.randomUUID()).unit(unit).uploadResult(uploadResult).build();
            }).toList();

            List<UnitImage> filteredOriginalUnitImages = unit.getImages().stream().filter(image -> {
                return dto.getUploadedFileIds().contains(image.getUploadResult().getId().toString());
            }).collect(Collectors.toList());
            filteredOriginalUnitImages.addAll(newUnitImages);
            unit.getImages().clear();
            unit.getImages().addAll(filteredOriginalUnitImages);
        } else {
            unit.getImages().clear();
        }

        return unit;
    }
}
