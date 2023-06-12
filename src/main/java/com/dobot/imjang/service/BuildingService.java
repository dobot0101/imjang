package com.dobot.imjang.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.dobot.imjang.dtos.BuildingCreateRequest;
import com.dobot.imjang.dtos.BuildingUpdateRequest;
import com.dobot.imjang.entities.Building;
import com.dobot.imjang.entities.Facility;
import com.dobot.imjang.entities.SchoolDistrict;
import com.dobot.imjang.entities.Transportation;
import com.dobot.imjang.enums.FacilityType;
import com.dobot.imjang.enums.SchoolType;
import com.dobot.imjang.enums.TransportationType;
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

    public Building createBuilding(BuildingCreateRequest req) {
        Building building = new Building();
        building.setAddress(req.getAddress());
        building.setName(req.getName());
        building.setEntranceStructure(req.getEntranceStructure());
        building.setElevatorStatus(req.getElevatorStatus());

        building.setLatitude(req.getLatitude());
        building.setLongitude(req.getLongitude());
        building.setParkingSpace(req.getParkingSpace());

        // 편의시설
        List<FacilityType> facilityTypes = req.getFacilityTypes();
        if (!facilityTypes.isEmpty()) {
            building.setFacilities(facilityTypes.stream().map(facilityType -> {
                Facility facility = new Facility();
                facility.setFacilityType(facilityType);
                return facility;
            }).collect(Collectors.toList()));
        }

        // 교통수단
        List<TransportationType> transportationTypes = req.getTransportationTypes();
        if (!transportationTypes.isEmpty()) {
            List<Transportation> transportations = transportationTypes.stream().map(transportationType -> {
                Transportation transportation = new Transportation();
                transportation.setTransportationType(transportationType);
                return transportation;
            }).collect(Collectors.toList());

            building.setTransportations(transportations);
        }

        // 학군
        List<SchoolType> schoolTypes = req.getSchoolTypes();
        if (!schoolTypes.isEmpty()) {
            List<SchoolDistrict> schoolDistricts = schoolTypes.stream().map(schoolType -> {
                SchoolDistrict schoolDistrict = new SchoolDistrict();
                schoolDistrict.setSchoolType(schoolType);
                return schoolDistrict;
            }).collect(Collectors.toList());
            building.setSchoolDistricts(schoolDistricts);
        }

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

        // List<Unit> units = updateReq.getUnits();
        // if (units.size() > 0) {
        // building.setUnits(units);
        // }

        // List<Facility> facilities = updateReq.getFacilities();
        // if (facilities.size() > 0) {
        // building.setFacilities(facilities);
        // }

        // List<Transportation> transportations = updateReq.getTransportations();
        // if (transportations.size() > 0) {
        // building.setTransportations(transportations);
        // }

        return buildingRepository.save(building);
    }

    public void deleteBuilding(UUID id) {
        buildingRepository.deleteById(id);
    }
}