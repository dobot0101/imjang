package com.dobot.imjang.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.dobot.imjang.dtos.BuildingCreateRequest;
import com.dobot.imjang.dtos.BuildingUpdateRequest;
import com.dobot.imjang.entities.Building;
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
            throw new NotFoundException("Home not found");
        }
        return optional.get();
    }

    public Building createBuilding(BuildingCreateRequest createReq) {
        Building home = new Building();
        home.setAddress(createReq.getAddress());
        home.setName(createReq.getName());
        return buildingRepository.save(home);
    }

    public Building updateBuilding(UUID id, BuildingUpdateRequest updateReq) {
        Optional<Building> homeOptional = buildingRepository.findById(id);
        if (!homeOptional.isPresent()) {
            throw new NotFoundException("Home not found");
        }

        Building home = homeOptional.get();
        home.setName(updateReq.getName());
        home.setAddress(updateReq.getAddress());

        return buildingRepository.save(home);
    }

    public void deleteBuilding(UUID id) {
        buildingRepository.deleteById(id);
    }
}
