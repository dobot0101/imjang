package com.dobot.imjang.domain.building.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.dobot.imjang.domain.building.dtos.BuildingRequest;
import com.dobot.imjang.domain.building.entities.Building;
import com.dobot.imjang.domain.building.entities.Facility;
import com.dobot.imjang.domain.building.entities.SchoolDistrict;
import com.dobot.imjang.domain.building.entities.Transportation;
import com.dobot.imjang.domain.building.enums.FacilityType;
import com.dobot.imjang.domain.building.enums.SchoolType;
import com.dobot.imjang.domain.building.enums.TransportationType;
import com.dobot.imjang.domain.building.repositories.BuildingRepository;
import com.dobot.imjang.domain.common.exceptions.DuplicateLocationException;
import com.dobot.imjang.domain.common.exceptions.ExceptionMessage;
import com.dobot.imjang.domain.common.exceptions.NotFoundException;

@Service
public class BuildingServiceImpl implements BuildingService {
    private final BuildingRepository buildingRepository;

    public BuildingServiceImpl(BuildingRepository buildingRepository) {
        this.buildingRepository = buildingRepository;
    }

    public List<Building> getAllBuildings() {
        return buildingRepository.findAll();
    }

    public Building getBuildingById(UUID id) {
        Optional<Building> optional = buildingRepository.findById(id);
        if (!optional.isPresent()) {
            throw new NotFoundException(ExceptionMessage.BUILDING_NOT_FOUND.getMessage());
        }
        return optional.get();
    }

    public Building createBuilding(BuildingRequest buildingRequest) {
        validBuildingRequest(buildingRequest);
        Building building = new Building();
        building.setId(UUID.randomUUID());
        setBuildingInformation(buildingRequest, building);
        return buildingRepository.save(building);
    }

    private void validBuildingRequest(BuildingRequest buildingRequest) throws Error {
        isDuplicatedLocation(buildingRequest);
    }

    private void isDuplicatedLocation(BuildingRequest buildingRequest) {
        double latitude = buildingRequest.getLatitude();
        double longitude = buildingRequest.getLongitude();

        List<Building> existingBuildings = this.buildingRepository.findByLatitudeAndLongitude(latitude, longitude);

        if (existingBuildings.size() > 0) {
            throw new DuplicateLocationException(Double.toString(latitude), Double.toString(longitude));
        }
    }

    public Building updateBuilding(UUID id, BuildingRequest buildingRequest) {
        Optional<Building> optional = buildingRepository.findById(id);
        if (!optional.isPresent()) {
            throw new NotFoundException(ExceptionMessage.BUILDING_NOT_FOUND.getMessage());
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
            facility.setBuilding(building);
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
