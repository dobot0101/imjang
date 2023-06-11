package com.dobot.imjang.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.dobot.imjang.dtos.BuildingCreateRequest;
import com.dobot.imjang.dtos.BuildingUpdateRequest;
import com.dobot.imjang.entities.Building;
import com.dobot.imjang.entities.Facility;
import com.dobot.imjang.entities.Transportation;
import com.dobot.imjang.entities.Unit;
import com.dobot.imjang.exceptions.NotFoundException;
import com.dobot.imjang.repository.BuildingRepository;

@Service
public class BuildingService {
    private final BuildingRepository buildingRepository;

    public BuildingService(BuildingRepository buildingRepository) {
        this.buildingRepository = buildingRepository;
    }

    public List<Building> getAllBuildings() {
        return buildingRepository.findAll();
    }

    public Building getBuildingById(UUID id) {
        Optional<Building> optional = buildingRepository.findById(id);
        if (!optional.isPresent()) {
            throw new NotFoundException("Building not found");
        }
        return optional.get();
    }

    public Building createBuilding(BuildingCreateRequest createReq) {
        Building building = new Building();
        building.setAddress(createReq.getAddress());
        building.setName(createReq.getName());
        building.setElevatorStatus(createReq.getElevatorStatus());
        building.setEntranceStructure(null);
        return buildingRepository.save(building);
    }

    public Building updateBuilding(UUID id, BuildingUpdateRequest updateReq) {
        Optional<Building> optional = buildingRepository.findById(id);
        if (!optional.isPresent()) {
            throw new NotFoundException("Building not found");
        }

        Building building = optional.get();
        building.setName(updateReq.getName());
        building.setAddress(updateReq.getAddress());

        List<Unit> units = updateReq.getUnits();
        if (units.size() > 0) {
            building.setUnits(units);
        }

        List<Facility> facilities = updateReq.getFacilities();
        if (facilities.size() > 0) {
            building.setFacilities(facilities);
        }

        List<Transportation> transportations = updateReq.getTransportations();
        if (transportations.size() > 0) {
            building.setTransportations(transportations);
        }

        return buildingRepository.save(building);
    }

    public void deleteBuilding(UUID id) {
        buildingRepository.deleteById(id);
    }
}
