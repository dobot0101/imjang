package com.dobot.imjang.domain.building.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.dobot.imjang.domain.auth.entity.CustomUserDetails;
import com.dobot.imjang.domain.building.dto.BuildingRegisterPageRequestDto;
import com.dobot.imjang.domain.building.entity.Building;
import com.dobot.imjang.domain.building.service.BuildingService;
import com.dobot.imjang.domain.permission.PermissionChecker;

@Controller
public class BuildingViewController {
  private final BuildingService buildingService;
  private final PermissionChecker permissionChecker;

  public BuildingViewController(BuildingService buildingService,
      PermissionChecker permissionChecker) {
    this.buildingService = buildingService;
    this.permissionChecker = permissionChecker;
  }

  @GetMapping("/")
  public String showHomePage(Model model) {
    CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
        .getPrincipal();
    List<Building> buildings = buildingService.getBuildingsByMemberId(userDetails.getId());

    model.addAttribute("buildings", buildings);
    model.addAttribute("username", userDetails.getUsername());

    return "home";
  }

  @GetMapping("/buildings/register")
  public String showBuildingRegisterPage(Model model, BuildingRegisterPageRequestDto dto) {
    model.addAttribute("latitude", dto.getLatitude());
    model.addAttribute("longitude", dto.getLongitude());

    String address = dto.getAddress();
    model.addAttribute("address", address == null ? "" : address);

    String buildingName = dto.getBuildingName();
    model.addAttribute("buildingName", buildingName == null ? "" : buildingName);

    return "create-building";
  }

  @GetMapping("/buildings/read/{id}")
  public String showBuildingReadPage(@PathVariable("id") String id, Model model) {
    Building building = buildingService.getBuildingById(UUID.fromString(id));
    permissionChecker.checkPermission(building.getMember().getId());
    model.addAttribute("building", building);

    List<String> schoolTypes = building.getSchoolDistricts().stream()
        .map(schoolDistrict -> schoolDistrict.getSchoolType().name()).toList();
    model.addAttribute("schoolTypes", schoolTypes);

    List<String> transportationTypes = building.getTransportations().stream()
        .map(transportation -> transportation.getTransportationType().name()).toList();
    model.addAttribute("transportationTypes", transportationTypes);

    List<String> facilityTypes = building.getFacilities().stream().map(facility -> facility.getFacilityType().name())
        .toList();
    model.addAttribute("facilityTypes", facilityTypes);

    return "read-building";
  }

  @GetMapping("/buildings/modify/{id}")
  public String showBuildingModifyPage(@PathVariable("id") String id, Model model) {
    Building building = buildingService.getBuildingById(UUID.fromString(id));
    permissionChecker.checkPermission(building.getMember().getId());
    model.addAttribute("building", building);

    List<String> schoolTypes = building.getSchoolDistricts().stream()
        .map(schoolDistrict -> schoolDistrict.getSchoolType().name()).toList();
    model.addAttribute("schoolTypes", schoolTypes);

    List<String> transportationTypes = building.getTransportations().stream()
        .map(transportation -> transportation.getTransportationType().name()).toList();
    model.addAttribute("transportationTypes", transportationTypes);

    List<String> facilityTypes = building.getFacilities().stream().map(facility -> facility.getFacilityType().name())
        .toList();
    model.addAttribute("facilityTypes", facilityTypes);

    return "update-building";
  }
}
