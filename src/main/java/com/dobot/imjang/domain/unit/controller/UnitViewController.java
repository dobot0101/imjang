package com.dobot.imjang.domain.unit.controller;

import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dobot.imjang.domain.building.entity.Building;
import com.dobot.imjang.domain.building.service.BuildingService;
import com.dobot.imjang.domain.permission.PermissionChecker;
import com.dobot.imjang.domain.unit.entity.Unit;
import com.dobot.imjang.domain.unit.service.UnitService;

@Controller
@RequestMapping("/units")
public class UnitViewController {
  private final BuildingService buildingService;
  private final UnitService unitService;
  private final PermissionChecker permissionChecker;

  public UnitViewController(BuildingService buildingService, UnitService unitService,
      PermissionChecker permissionChecker) {
    this.buildingService = buildingService;
    this.unitService = unitService;
    this.permissionChecker = permissionChecker;
  }

  @GetMapping("/new/{buildingId}")
  public String showUnitCreateForm(Model model, @PathVariable("buildingId") String buildingId) {
    Building building = this.buildingService.getBuildingById(UUID.fromString(buildingId));
    permissionChecker.checkPermission(building.getMember().getId());

    return "create-unit";
  }

  @GetMapping("/{unitId}")
  public String showUnitReadForm(Model model, @PathVariable("unitId") String unitId) {
    Unit unit = unitService.getUnitById(UUID.fromString(unitId));
    model.addAttribute("unit", unit);
    
    return "read-unit";
  }

  @GetMapping("/{unitId}/edit")
  public String showUnitUpdateForm(Model model, @PathVariable("unitId") String unitId) {
    Unit unit = unitService.getUnitById(UUID.fromString(unitId));
    model.addAttribute("unit", unit);
    
    return "update-unit";
  }
}
