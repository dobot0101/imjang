package com.dobot.imjang.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dobot.imjang.dtos.BuildingRequest;
import com.dobot.imjang.entities.Building;
import com.dobot.imjang.service.BuildingService;

@RestController
@RequestMapping("/building")
public class BuildingController {
  private final BuildingService buildingService;

  public BuildingController(BuildingService buildingService) {
    this.buildingService = buildingService;
  }

  @GetMapping("")
  public List<Building> getAllBuildings() {
    return buildingService.getAllBuildings();
  }

  @GetMapping("/{id}")
  public Building getBuilding(@PathVariable("id") UUID id) {
    return buildingService.getBuildingById(id);
  }

  @PostMapping("")
  public ResponseEntity<Map<String, String>> createBuilding(
      @RequestBody @Validated BuildingRequest buildingCreateRequest) {
    Building building = buildingService.createBuilding(buildingCreateRequest);
    Map<String, String> response = new HashMap<String, String>();
    response.put("savedBuildingId", building.getId().toString());
    return ResponseEntity.ok(response);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Map<String, String>> updateBuilding(@PathVariable("id") UUID id,
      @RequestBody @Validated BuildingRequest buildingUpdateRequest) {
    Building building = buildingService.updateBuilding(id, buildingUpdateRequest);
    Map<String, String> response = new HashMap<String, String>();
    response.put("updatedBuildingId", building.getId().toString());
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{id}")
  public void deleteBuilding(@PathVariable("id") UUID id) {
    buildingService.deleteBuilding(id);
  }
}