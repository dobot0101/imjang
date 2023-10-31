package com.dobot.imjang.domain.unit.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.core.context.SecurityContextHolder;

import com.dobot.imjang.domain.building.entities.Building;
import com.dobot.imjang.domain.building.repositories.BuildingRepository;
import com.dobot.imjang.domain.common.exception.CustomException;
import com.dobot.imjang.domain.common.exception.ErrorCode;
import com.dobot.imjang.domain.unit.dtos.UnitCreateOrUpdateDto;
import com.dobot.imjang.domain.unit.entities.Unit;
import com.dobot.imjang.domain.unit.entities.UnitImage;
import com.dobot.imjang.domain.unit.entities.UnitTransaction;
import com.dobot.imjang.domain.unit.repositories.UnitRepository;

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

  public Unit createUnit(UnitCreateOrUpdateDto dto) {
    Building building = this.buildingRepository.findById(UUID.fromString(dto.getBuidlingId()))
        .orElseThrow(() -> new CustomException(ErrorCode.BUILDING_NOT_FOUND));

    String email = building.getMember().getEmail();
    if (!SecurityContextHolder.getContext().getAuthentication().getName().equals(email)) {
      throw new CustomException(ErrorCode.PERMISSION_DENIED);
    }

    Unit unit = new Unit();
    unit = addUnitProperties(dto, unit);
    return this.unitRepository.save(unit);
  }

  public Unit updateUnit(UUID id, UnitCreateOrUpdateDto unitRequest) {
    Optional<Unit> optional = this.unitRepository.findById(id);
    if (optional.isEmpty()) {
      throw new CustomException(ErrorCode.UNIT_NOT_FOUND);
    }

    Unit unit = optional.get();
    Unit updatingUnit = this.addUnitProperties(unitRequest, unit);
    return this.unitRepository.save(updatingUnit);
  }

  public void deleteUnit(UUID id) {
    this.unitRepository.deleteById(id);
  }

  private Unit addUnitProperties(UnitCreateOrUpdateDto dto, Unit unit) {
    Building building = buildingRepository.findById(UUID.fromString(dto.getBuidlingId())).orElseThrow(
        () -> new CustomException(ErrorCode.BUILDING_NOT_FOUND, "건물 정보가 존재하지 않습니다. 건물 아이디: " + dto.getBuidlingId()));
    unit.setBuilding(building);

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

    List<UnitTransaction> unitTransactions = dto.getUnitTransactionDtos().stream().map(
        unitTransactionDto -> new UnitTransaction(UUID.randomUUID(), unitTransactionDto.getPrice(),
            unitTransactionDto.getDeposit(), unit, unitTransactionDto.getTransactionType()))
        .toList();
    unit.setTransactions(unitTransactions);

    List<UnitImage> unitImages = dto.getImageUrls().stream()
        .map(imageUrl -> new UnitImage(UUID.randomUUID(), imageUrl, unit)).toList();
    unit.setImages(unitImages);

    return unit;
  }
}
