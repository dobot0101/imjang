package com.dobot.imjang.domain.building.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.dobot.imjang.domain.building.entity.Building;
import com.dobot.imjang.domain.building.service.BuildingService;

@Controller
public class BuildingViewController {
  private final BuildingService buildingService;

  public BuildingViewController(BuildingService buildingService) {
    this.buildingService = buildingService;
  }

  @GetMapping("/building/register")
  public String showBuildingRegisterPage(Model model, @RequestParam("lat") double latitude,
      @RequestParam("lng") double longitude, @RequestParam("address") String address,
      @RequestParam("buildingName") String buildingName) {
    model.addAttribute("latitude", latitude);
    model.addAttribute("longitude", longitude);
    model.addAttribute("address", address == null ? "" : address);
    model.addAttribute("buildingName", buildingName == null ? "" : buildingName);
    return "create-building";
  }

  @GetMapping("/building/read/{id}")
  public String showBuildingReadPage(@PathVariable("id") String id, Model model) {
    Building building = buildingService.getBuildingById(UUID.fromString(id));
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

  @GetMapping("/building/modify/{id}")
  public String showBuildingModifyPage(@PathVariable("id") String id, Model model) {
    Building building = buildingService.getBuildingById(UUID.fromString(id));
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

    return "modify-building";
  }
}
