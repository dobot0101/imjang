package com.dobot.imjang.domain.unit.controller;

import java.util.UUID;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dobot.imjang.domain.building.entity.Building;
import com.dobot.imjang.domain.building.service.BuildingService;
import com.dobot.imjang.domain.common.exception.CustomException;
import com.dobot.imjang.domain.common.exception.ErrorCode;
import com.dobot.imjang.domain.unit.entity.Unit;
import com.dobot.imjang.domain.unit.service.UnitService;

@Controller
@RequestMapping("/units")
public class UnitViewController {
  private final BuildingService buildingService;
  private final UnitService unitService;

  public UnitViewController(BuildingService buildingService, UnitService unitService) {
    this.buildingService = buildingService;
    this.unitService = unitService;
  }

  @GetMapping("/new/${buildingId}")
  public String showUnitCreateForm(Model model, @RequestParam("bulidingId") String buildingId) {
    // Building 정보의 주인과 Unit을 등록하는 유저가 같지 않으면 권한 에러를 발생시킨다.
    String email = SecurityContextHolder.getContext().getAuthentication().getName();
    Building building = this.buildingService.getBuildingById(UUID.fromString(buildingId));
    if (!building.getMember().getEmail().equals(email)) {
      throw new CustomException(ErrorCode.PERMISSION_DENIED);
    }
    return "create-unit";
  }

  @GetMapping("/${unitId}")
  public String showUnitReadForm(Model model, @RequestParam("unitId") String unitId) {
    Unit unit = unitService.getUnitById(UUID.fromString(unitId));
    model.addAttribute("unit", unit);
    return "read-unit";
  }

  @GetMapping("/${unitId}/edit")
  public String showUnitUpdateForm(Model model, @RequestParam("unitId") String unitId) {
    Unit unit = unitService.getUnitById(UUID.fromString(unitId));
    model.addAttribute("unit", unit);
    return "update-unit";
  }
}
