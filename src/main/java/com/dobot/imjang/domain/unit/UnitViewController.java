package com.dobot.imjang.domain.unit;

import com.dobot.imjang.domain.building.Building;
import com.dobot.imjang.domain.building.BuildingService;
import com.dobot.imjang.domain.permission.PermissionChecker;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequestMapping("/units")
public class UnitViewController {
    private final BuildingService buildingService;
    private final UnitService unitService;
    private final PermissionChecker permissionChecker;

    public UnitViewController(BuildingService buildingService, UnitService unitService, PermissionChecker permissionChecker) {
        this.buildingService = buildingService;
        this.unitService = unitService;
        this.permissionChecker = permissionChecker;
    }

    @GetMapping("/new/{buildingId}")
    public String showUnitCreateForm(Model model, @PathVariable("buildingId") String buildingId) {
        Building building = this.buildingService.getBuildingById(UUID.fromString(buildingId));
        permissionChecker.checkPermission(building.getMember().getId());

        model.addAttribute("buildingId", buildingId);
        model.addAttribute("headerPageName", "세대정보 등록");

        return "create-unit";
    }

    @GetMapping("/{unitId}")
    public String showUnitReadForm(Model model, @PathVariable("unitId") String unitId) {
        Unit unit = unitService.getUnitById(UUID.fromString(unitId));
        model.addAttribute("unit", unit);
        model.addAttribute("headerPageName", "세대정보");

        return "read-unit";
    }

    @GetMapping("/{unitId}/edit")
    public String showUnitUpdateForm(Model model, @PathVariable("unitId") String unitId) {
        Unit unit = unitService.getUnitById(UUID.fromString(unitId));
        model.addAttribute("unit", unit);
        model.addAttribute("headerPageName", "세대정보 수정");

        return "update-unit";
    }
}
