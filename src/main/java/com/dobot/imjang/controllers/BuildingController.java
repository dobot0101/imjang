package com.dobot.imjang.controllers;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

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
  public Building createBuilding(@RequestBody @Valid BuildingRequest buildingCreateRequest) {
    return buildingService.createBuilding(buildingCreateRequest);
  }

  @PutMapping("/{id}")
  public Building updateBuilding(@PathVariable("id") UUID id,
      @RequestBody @Valid BuildingRequest buildingUpdateRequest) {
    return buildingService.updateBuilding(id, buildingUpdateRequest);
  }

  @DeleteMapping("/{id}")
  public void deleteBuilding(@PathVariable("id") UUID id) {
    buildingService.deleteBuilding(id);
  }
}