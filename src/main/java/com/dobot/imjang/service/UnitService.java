package com.dobot.imjang.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import com.dobot.imjang.dtos.UnitCreateRequest;
import com.dobot.imjang.dtos.UnitUpdateRequest;
import com.dobot.imjang.entities.Building;
import com.dobot.imjang.entities.Unit;
import com.dobot.imjang.entities.UnitImage;
import com.dobot.imjang.exception.NotFoundException;
import com.dobot.imjang.repository.BuildingRepository;
import com.dobot.imjang.repository.UnitRepository;

public class UnitService {
  private final UnitRepository unitRepository;
  private final BuildingRepository buildingRepository;

  public UnitService(UnitRepository unitRepository, BuildingRepository buildingRepository) {
    this.unitRepository = unitRepository;
    this.buildingRepository = buildingRepository;
  }

  public List<Unit> getAllUnits() {
    return this.unitRepository.findAll();
  }

  public Unit getUnitById(UUID id) {
    Optional<Unit> optional = this.unitRepository.findById(id);
    if (!optional.isPresent()) {
      throw new NotFoundException("Unit not found");
    }
    return optional.get();
  }

  public Unit createUnit(UnitCreateRequest req) {
    Building building = getOrCreateBuilding(req);

    Unit unit = new Unit();
    unit.setArea(req.getArea());
    unit.setBuildingNumber(req.getBuildingNumber());
    unit.setCondensationMoldLevel(req.getCondensationMoldLevel());
    unit.setDeposit(req.getDeposit());
    unit.setDirection(req.getDirection());
    unit.setLeakStatus(req.getLeakStatus());
    unit.setMemo(req.getMemo());
    unit.setNoiseLevel(req.getNoiseLevel());
    unit.setRoomNumber(req.getRoomNumber());
    unit.setTransactionPrice(req.getTransactionPrice());
    unit.setTransactionTypes(req.getTransactionType());
    unit.setVentilation(req.getVentilation());
    unit.setViewQuality(req.getViewQuality());
    unit.setWaterPressure(req.getWaterPressure());

    List<UnitImage> unitImages = getUnitImages(req.getImageUrls());
    if (unitImages.size() > 0) {
      unit.setImages(unitImages);
    }

    List<Unit> unitList = building.getUnits();
    unitList.add(unit);

    this.buildingRepository.save(building);

    return unit;
  }

  public Unit updateUnit(UnitUpdateRequest req) {
    Unit unit = this.getUnitById(req.getId());

    unit.setArea(req.getArea());
    unit.setBuildingNumber(req.getBuildingNumber());
    unit.setCondensationMoldLevel(req.getCondensationMoldLevel());
    unit.setDeposit(req.getDeposit());
    unit.setDirection(req.getDirection());
    unit.setLeakStatus(req.getLeakStatus());
    unit.setMemo(req.getMemo());
    unit.setNoiseLevel(req.getNoiseLevel());
    unit.setRoomNumber(req.getRoomNumber());
    unit.setTransactionPrice(req.getTransactionPrice());
    unit.setTransactionTypes(req.getTransactionType());
    unit.setVentilation(req.getVentilation());
    unit.setViewQuality(req.getViewQuality());
    unit.setWaterPressure(req.getWaterPressure());

    List<UnitImage> unitImages = getUnitImages(req.getImageUrls());
    if (unitImages.size() > 0) {
      unit.setImages(unitImages);
    }

    this.unitRepository.save(unit);

    return unit;
  }

  private List<UnitImage> getUnitImages(List<String> imageUrls) {
    List<UnitImage> unitImages = new ArrayList<UnitImage>();
    if (imageUrls.isEmpty()) {
      return unitImages;
    }

    unitImages = imageUrls.stream().map(imageUrl -> {
      UnitImage image = new UnitImage();
      image.setImageUrl(imageUrl);
      return image;
    }).collect(Collectors.toList());

    return unitImages;
  }

  public void deleteUnit(UUID id) {
    this.unitRepository.deleteById(id);
  }

  private Building getOrCreateBuilding(UnitCreateRequest req) {
    Building building;
    if (req.getBuidlingId() != null) {
      Optional<Building> optional = this.buildingRepository.findById(req.getBuidlingId());
      if (!optional.isPresent()) {
        throw new NotFoundException("Building not found");
      }
      building = optional.get();
    } else {
      building = new Building();
      building.setAddress(null);
      building.setElevatorStatus(null);
      building.setEntranceStructure(null);
      building.setFacilities(null);
      building.setLatitude(0);
      building.setLongitude(0);
      building.setName(null);
      building.setParkingSpace(null);
      building.setSchoolDistricts(null);
      building.setTransportations(null);
    }
    return building;
  }
}
