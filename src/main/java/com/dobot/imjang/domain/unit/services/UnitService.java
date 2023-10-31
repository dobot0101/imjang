package com.dobot.imjang.domain.unit.services;

import java.util.List;
import java.util.UUID;

import com.dobot.imjang.domain.unit.dtos.UnitCreateOrUpdateDto;
import com.dobot.imjang.domain.unit.entities.Unit;

public interface UnitService {
  public List<Unit> getAllUnits();

  public Unit createUnit(UnitCreateOrUpdateDto unitRequest);

  public Unit updateUnit(UUID id, UnitCreateOrUpdateDto unitRequest);

  public void deleteUnit(UUID id);
}
