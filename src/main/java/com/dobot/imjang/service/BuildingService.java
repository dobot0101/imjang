package com.dobot.imjang.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.dobot.imjang.dtos.BuildingRequest;
import com.dobot.imjang.entities.Building;
import com.dobot.imjang.entities.Facility;
import com.dobot.imjang.entities.SchoolDistrict;
import com.dobot.imjang.entities.Transportation;
import com.dobot.imjang.enums.FacilityType;
import com.dobot.imjang.enums.SchoolType;
import com.dobot.imjang.enums.TransportationType;
import com.dobot.imjang.exception.NotFoundException;
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

    public Building createBuilding(BuildingRequest buildingRequest) {
        Building building = new Building();
        building.setId(UUID.randomUUID());
        setBuildingInformation(buildingRequest, building);
        return buildingRepository.save(building);
    }

    public Building updateBuilding(UUID id, BuildingRequest buildingRequest) {
        Optional<Building> optional = buildingRepository.findById(id);
        if (!optional.isPresent()) {
            throw new NotFoundException("Building not found");
        }

        Building building = optional.get();
        this.setBuildingInformation(buildingRequest, building);

        return buildingRepository.save(building);
    }

    public void deleteBuilding(UUID id) {
        buildingRepository.deleteById(id);
    }

    private Building setBuildingInformation(BuildingRequest buildingRequest, Building building) {
        building.setAddress(buildingRequest.getAddress());
        building.setName(buildingRequest.getName());
        building.setEntranceStructure(buildingRequest.getEntranceStructure());
        building.setElevatorStatus(buildingRequest.getElevatorStatus());
        building.setLatitude(buildingRequest.getLatitude());
        building.setLongitude(buildingRequest.getLongitude());
        building.setParkingSpace(buildingRequest.getParkingSpace());
        // building.setCreatedDate(LocalDateTime.now());

        // 학군
        List<SchoolType> schoolTypes = buildingRequest.getSchoolTypes();
        List<SchoolDistrict> schoolDistricts = Optional.ofNullable(schoolTypes)
                .map(types -> types.stream()
                        .map(schoolType -> {
                            SchoolDistrict schoolDistrict = new SchoolDistrict();
                            schoolDistrict.setId(UUID.randomUUID());
                            schoolDistrict.setSchoolType(schoolType);
                            return schoolDistrict;
                        })
                        .collect(Collectors.toList()))
                .orElse(null);
        building.setSchoolDistricts(schoolDistricts);

        // 편의시설
        List<FacilityType> facilityTypes = buildingRequest.getFacilityTypes();
        List<Facility> facilities = Optional.ofNullable(facilityTypes).map(types -> types.stream().map(facilityType -> {
            Facility facility = new Facility();
            facility.setId(UUID.randomUUID());
            facility.setFacilityType(facilityType);
            return facility;
        }).collect(Collectors.toList())).orElse(null);
        building.setFacilities(facilities);

        // 교통수단
        List<TransportationType> transportationTypes = buildingRequest.getTransportationTypes();
        List<Transportation> transportations = Optional.ofNullable(transportationTypes)
                .map(types -> types.stream().map(transportationType -> {
                    Transportation transportation = new Transportation();
                    transportation.setId(UUID.randomUUID());
                    transportation.setTransportationType(transportationType);
                    return transportation;
                }).collect(Collectors.toList())).orElse(null);
        building.setTransportations(transportations);

        return building;
    }
}
