package com.dobot.imjang.domain.unit.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import com.dobot.imjang.domain.building.entities.Building;
import com.dobot.imjang.domain.building.repositories.BuildingRepository;
import com.dobot.imjang.domain.common.exceptions.ExceptionMessage;
import com.dobot.imjang.domain.common.exceptions.NotFoundException;
import com.dobot.imjang.domain.unit.dtos.UnitRequest;
import com.dobot.imjang.domain.unit.entities.Unit;
import com.dobot.imjang.domain.unit.entities.UnitImage;
import com.dobot.imjang.domain.unit.entities.UnitTransactionType;
import com.dobot.imjang.domain.unit.enums.TransactionType;
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

  public Unit createUnit(UnitRequest unitRequest) {
    Optional<Building> optional = this.buildingRepository.findById(unitRequest.getBuidlingId());
    if (optional.isEmpty()) {
      throw new NotFoundException(ExceptionMessage.BUILDING_NOT_FOUND.getMessage());
    }

    Unit unit = new Unit();
    unit = addUnitProperties(unitRequest, unit);
    return this.unitRepository.save(unit);
  }

  public Unit updateUnit(UUID id, UnitRequest unitRequest) {
    Optional<Unit> optional = this.unitRepository.findById(id);
    if (optional.isEmpty()) {
      throw new NotFoundException(ExceptionMessage.UNIT_NOT_FOUND.getMessage());
    }

    Unit unit = optional.get();
    Unit updatingUnit = this.addUnitProperties(unitRequest, unit);
    return this.unitRepository.save(updatingUnit);
  }

  private List<UnitImage> getUnitImages(List<String> imageUrls) {
    List<UnitImage> unitImages = imageUrls.stream().map(imageUrl -> {
      UnitImage image = new UnitImage();
      image.setImageUrl(imageUrl);
      return image;
    }).collect(Collectors.toList());

    return unitImages;
  }

  public void deleteUnit(UUID id) {
    this.unitRepository.deleteById(id);
  }

  private Unit addUnitProperties(UnitRequest unitRequest, Unit unit) {
    unit.setArea(unitRequest.getArea());
    unit.setBuildingNumber(unitRequest.getBuildingNumber());
    unit.setCondensationMoldLevel(unitRequest.getCondensationMoldLevel());
    unit.setDeposit(unitRequest.getDeposit());
    unit.setDirection(unitRequest.getDirection());
    unit.setLeakStatus(unitRequest.getLeakStatus());
    unit.setMemo(unitRequest.getMemo());
    unit.setNoiseLevel(unitRequest.getNoiseLevel());
    unit.setRoomNumber(unitRequest.getRoomNumber());
    unit.setTransactionPrice(unitRequest.getTransactionPrice());
    unit.setVentilation(unitRequest.getVentilation());
    unit.setViewQuality(unitRequest.getViewQuality());
    unit.setWaterPressure(unitRequest.getWaterPressure());

    List<TransactionType> transactionTypes = unitRequest.getTransactionTypes();
    List<UnitTransactionType> unitTransactionTypes = transactionTypes.stream().map(t -> {
      UnitTransactionType unitTransactionType = new UnitTransactionType();
      unitTransactionType.setId(UUID.randomUUID());
      unitTransactionType.setTransactionType(t);
      return unitTransactionType;
    }).collect(Collectors.toList());
    unit.setTransactionTypes(unitTransactionTypes);

    List<UnitImage> unitImages = getUnitImages(unitRequest.getImageUrls());
    if (unitImages.size() > 0) {
      unit.setImages(unitImages);
    }

    return unit;
  }
}
