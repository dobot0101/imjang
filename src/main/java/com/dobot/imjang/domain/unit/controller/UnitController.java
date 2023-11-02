package com.dobot.imjang.domain.unit.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dobot.imjang.domain.building.entity.Building;
import com.dobot.imjang.domain.building.service.BuildingService;
import com.dobot.imjang.domain.permission.PermissionChecker;
import com.dobot.imjang.domain.unit.dto.UnitCreateOrUpdateDto;
import com.dobot.imjang.domain.unit.entity.Unit;
import com.dobot.imjang.domain.unit.service.UnitService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/api/units")
public class UnitController {
  private final UnitService unitService;
  private final BuildingService buildingService;
  private final PermissionChecker permissionChecker;

  public UnitController(UnitService unitService, BuildingService buildingService, PermissionChecker permissionChecker) {
    this.unitService = unitService;
    this.buildingService = buildingService;
    this.permissionChecker = permissionChecker;
  }

  @PostMapping("")
  public ResponseEntity<Map<String, String>> createUnit(
      @RequestBody @Valid UnitCreateOrUpdateDto dto) {
    Building building = buildingService.getBuildingById(UUID.fromString(dto.getBuildingId()));
    permissionChecker.checkPermission(building.getMember().getId());

    Unit unit = unitService.createUnit(dto);
    Map<String, String> response = new HashMap<String, String>();
    response.put("savedUnitId", unit.getId().toString());
    return ResponseEntity.ok(response);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Map<String, String>> updateUnit(@PathVariable("id") UUID id,
      @RequestBody @Valid UnitCreateOrUpdateDto dto) {
    Unit unit = unitService.getUnitById(id);
    permissionChecker.checkPermission(unit.getMember().getId());

    Unit updatedUnit = unitService.updateUnit(id, dto);
    Map<String, String> response = new HashMap<String, String>();
    response.put("savedUnitId", updatedUnit.getId().toString());
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Map<String, String>> deleteUnit(@PathVariable("id") UUID id) {
    Unit unit = unitService.getUnitById(id);
    permissionChecker.checkPermission(unit.getMember().getId());

    unitService.deleteUnit(id);
    Map<String, String> response = new HashMap<>();
    response.put("result", "success");
    return ResponseEntity.ok(response);
  }
}
