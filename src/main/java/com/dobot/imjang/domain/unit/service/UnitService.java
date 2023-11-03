package com.dobot.imjang.domain.unit.service;

import java.util.List;
import java.util.UUID;

import com.dobot.imjang.domain.unit.dto.UnitCreateOrUpdateDto;
import com.dobot.imjang.domain.unit.entity.Unit;

public interface UnitService {
  public List<Unit> getAllUnits();

  public Unit getUnitById(UUID id);

  public Unit createUnit(UnitCreateOrUpdateDto unitCreateOrUpdateDto, UUID buildingId);

  public Unit updateUnit(UUID id, UnitCreateOrUpdateDto unitCreateOrUpdateDto);

  public void deleteUnitById(UUID id);
}
