package com.dobot.imjang.domain.unit.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.dobot.imjang.domain.building.entity.Building;
import com.dobot.imjang.domain.building.repository.BuildingRepository;
import com.dobot.imjang.domain.common.exception.CustomException;
import com.dobot.imjang.domain.common.exception.ErrorCode;
import com.dobot.imjang.domain.unit.dto.UnitCreateOrUpdateDto;
import com.dobot.imjang.domain.unit.entity.Unit;
import com.dobot.imjang.domain.unit.entity.UnitImage;
import com.dobot.imjang.domain.unit.repository.UnitRepository;

@Service
public class UnitServiceImpl implements UnitService {
  private final UnitRepository unitRepository;
  private final BuildingRepository buildingRepository;

  public UnitServiceImpl(UnitRepository unitRepository, BuildingRepository buildingRepository) {
    this.unitRepository = unitRepository;
    this.buildingRepository = buildingRepository;
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
    unit.setMember(building.getMember());
    unit = addUnitProperties(dto, unit);

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

  @Override
  public Unit getUnitById(UUID id) {
    Unit unit = unitRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.UNIT_NOT_FOUND));
    return unit;
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

    if (!dto.getImageUrls().isEmpty()) {
      List<UnitImage> unitImages = dto.getImageUrls().stream()
          .map(imageUrl -> new UnitImage(UUID.randomUUID(), imageUrl, unit)).toList();
      unit.setImages(unitImages);
    }

    return unit;
  }
}
