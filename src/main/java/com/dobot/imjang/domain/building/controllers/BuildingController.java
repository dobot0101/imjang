package com.dobot.imjang.domain.building.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dobot.imjang.domain.building.dtos.BuildingCreateOrUpdateRequestDto;
import com.dobot.imjang.domain.building.entities.Building;
import com.dobot.imjang.domain.building.services.BuildingService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/buildings")
public class BuildingController {
  private final BuildingService buildingService;

  public BuildingController(BuildingService buildingService) {
    this.buildingService = buildingService;
  }

  @GetMapping("")
  public ResponseEntity<Map<String, Object>> getAllBuildings() {
    List<Building> buildings = buildingService.getAllBuildings();
    Map<String, Object> map = new HashMap<>();
    map.put("buildings", buildings);
    return ResponseEntity.ok(map);
  }

  @GetMapping("/{id}")
  public Building getBuilding(@PathVariable("id") UUID id) {
    return buildingService.getBuildingById(id);
  }

  @PostMapping("")
  public ResponseEntity<Map<String, String>> createBuilding(
      @RequestBody @Valid BuildingCreateOrUpdateRequestDto buildingCreateOrUpdateRequestDto) {
    Building building = buildingService.createBuilding(buildingCreateOrUpdateRequestDto);
    Map<String, String> response = new HashMap<String, String>();
    response.put("savedBuildingId", building.getId().toString());
    return ResponseEntity.ok(response);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Map<String, String>> updateBuilding(@PathVariable("id") UUID id,
      @RequestBody @Valid BuildingCreateOrUpdateRequestDto bulidingCreateOrUpdateDto) {
    Building building = buildingService.updateBuilding(id, bulidingCreateOrUpdateDto);
    Map<String, String> response = new HashMap<String, String>();
    response.put("savedBuildingId", building.getId().toString());
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Map<String, String>> deleteBuilding(@PathVariable("id") UUID id) {
    buildingService.deleteBuilding(id);
    Map<String, String> response = new HashMap<>();
    response.put("result", "success");
    return ResponseEntity.ok(response);
  }
}