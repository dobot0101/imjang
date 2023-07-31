package com.dobot.imjang.domain.unit.services;

import java.util.List;
import java.util.UUID;

import com.dobot.imjang.domain.unit.dtos.UnitRequest;
import com.dobot.imjang.domain.unit.entities.Unit;

public interface UnitService {
  public List<Unit> getAllUnits();

  public Unit createUnit(UnitRequest unitRequest);

  public Unit updateUnit(UUID id, UnitRequest unitRequest);

  public void deleteUnit(UUID id);
}
