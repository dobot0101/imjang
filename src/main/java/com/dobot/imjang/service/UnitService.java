package com.dobot.imjang.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.dobot.imjang.dtos.UnitCreateRequest;
import com.dobot.imjang.dtos.UnitUpdateRequest;
import com.dobot.imjang.entities.Unit;
import com.dobot.imjang.exceptions.NotFoundException;
import com.dobot.imjang.repository.UnitRepository;

public class UnitService {
  private final UnitRepository unitRepository;

  public UnitService(UnitRepository unitRepository) {
    this.unitRepository = unitRepository;
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
    Unit unit = new Unit();
    unit.setArea(req.getArea());
    // unit.setBuilding(req.getBuilding());
    unit.setBuildingNumber(req.getBuildingNumber());
    unit.setCondensationMoldLevel(req.getCondensationMoldLevel());
    unit.setDeposit(req.getDeposit());
    unit.setDirection(req.getDirection());
    unit.setLeakStatus(req.getLeakStatus());
    unit.setMemo(req.getMemo());
    unit.setNoiseLevel(req.getNoiseLevel());
    unit.setRoomNumber(req.getRoomNumber());
    unit.setTransactionPrice(req.getTransactionPrice());
    unit.setTransactionType(req.getTransactionType());
    unit.setVentilation(req.getVentilation());
    unit.setViewQuality(req.getViewQuality());
    unit.setWaterPressure(req.getWaterPressure());
    return unit;
  }

  // public Unit updateUnit(UnitUpdateRequest req) {

  // }

  public void deleteUnit(UUID id) {
    this.unitRepository.deleteById(id);
  }

}
