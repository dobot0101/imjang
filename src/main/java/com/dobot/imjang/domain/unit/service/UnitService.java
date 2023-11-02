package com.dobot.imjang.domain.unit.service;

import java.util.List;
import java.util.UUID;

import com.dobot.imjang.domain.unit.dto.UnitCreateOrUpdateDto;
import com.dobot.imjang.domain.unit.entity.Unit;

public interface UnitService {
  public List<Unit> getAllUnits();

  public Unit getUnitById(UUID id);

  public Unit createUnit(UnitCreateOrUpdateDto unitRequest);

  public Unit updateUnit(UUID id, UnitCreateOrUpdateDto unitRequest);

  public void deleteUnit(UUID id);
}