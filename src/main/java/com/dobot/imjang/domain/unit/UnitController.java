package com.dobot.imjang.domain.unit;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.dobot.imjang.domain.building.BuildingService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dobot.imjang.domain.building.Building;
import com.dobot.imjang.domain.permission.PermissionChecker;

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

  @PostMapping("/new/{buildingId}")
  public ResponseEntity<Map<String, String>> createUnit(
      @PathVariable("buildingId") String buildingId, @RequestBody @Valid UnitCreateOrUpdateDto dto) {
    UUID buildingUUID = UUID.fromString(buildingId);
    Building building = buildingService.getBuildingById(buildingUUID);
    permissionChecker.checkPermission(building.getMember().getId());

    Unit unit = unitService.createUnit(dto, buildingUUID);
    Map<String, String> response = new HashMap<String, String>();
    response.put("savedUnitId", unit.getId().toString());
    return ResponseEntity.ok(response);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Map<String, String>> updateUnit(@PathVariable("id") UUID id,
      @RequestBody @Valid UnitCreateOrUpdateDto dto) {
    Unit unit = unitService.getUnitById(id);
    permissionChecker.checkPermission(unit.getBuilding().getMember().getId());

    Unit updatedUnit = unitService.updateUnit(id, dto);
    Map<String, String> response = new HashMap<String, String>();
    response.put("savedUnitId", updatedUnit.getId().toString());
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Map<String, String>> deleteUnit(@PathVariable("id") UUID id) {
    Unit unit = unitService.getUnitById(id);
    permissionChecker.checkPermission(unit.getBuilding().getMember().getId());

    unitService.deleteUnitById(id);
    Map<String, String> response = new HashMap<>();
    response.put("result", "success");
    return ResponseEntity.ok(response);
  }
}
