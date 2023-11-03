package com.dobot.imjang.domain.building.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.dobot.imjang.domain.building.dto.BuildingCreateOrUpdateRequestDto;
import com.dobot.imjang.domain.building.entity.Building;
import com.dobot.imjang.domain.building.entity.Facility;
import com.dobot.imjang.domain.building.entity.SchoolDistrict;
import com.dobot.imjang.domain.building.entity.Transportation;
import com.dobot.imjang.domain.building.enums.FacilityType;
import com.dobot.imjang.domain.building.enums.SchoolType;
import com.dobot.imjang.domain.building.enums.TransportationType;
import com.dobot.imjang.domain.building.repository.BuildingRepository;
import com.dobot.imjang.domain.common.exception.CustomException;
import com.dobot.imjang.domain.common.exception.ErrorCode;
import com.dobot.imjang.domain.member.entity.Member;

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
        Building building = buildingRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.BUILDING_NOT_FOUND));
        return building;
    }

    public Building createBuilding(BuildingCreateOrUpdateRequestDto dto, Member member) {
        isDuplicatedLocation(dto);
        Building building = new Building();
        building.setId(UUID.randomUUID());
        building.setLongitude(dto.getLongitude());
        building.setLatitude(dto.getLatitude());
        building.setMember(member);

        setBuildingInformation(dto, building);

        return buildingRepository.save(building);
    }

    private void isDuplicatedLocation(BuildingCreateOrUpdateRequestDto dto) {
        double latitude = dto.getLatitude();
        double longitude = dto.getLongitude();

        List<Building> existingBuildings = this.buildingRepository.findByLatitudeAndLongitude(latitude, longitude);

        if (existingBuildings.size() > 0) {
            throw new CustomException(ErrorCode.DUPLICATE_LOCATION,
                    "좌표 중복 에러 [" + latitude + ", " + longitude + "]");
        }
    }

    public Building updateBuilding(UUID id, BuildingCreateOrUpdateRequestDto dto) {
        Building building = buildingRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.BUILDING_NOT_FOUND));

        this.setBuildingInformation(dto, building);

        return buildingRepository.save(building);
    }

    public void deleteBuilding(UUID id) {
        buildingRepository.deleteById(id);
    }

    private Building setBuildingInformation(BuildingCreateOrUpdateRequestDto dto, Building building) {
        building.setAddress(dto.getAddress());
        building.setName(dto.getName());
        building.setEntranceStructure(dto.getEntranceStructure());
        building.setElevatorStatus(dto.getElevatorStatus());
        building.setParkingSpace(dto.getParkingSpace());

        // 학군
        List<SchoolType> schoolTypes = dto.getSchoolTypes();
        List<SchoolDistrict> newSchoolDistricts = Optional.ofNullable(schoolTypes)
                .map(types -> types.stream()
                        .map(schoolType -> {
                            SchoolDistrict schoolDistrict = new SchoolDistrict();
                            schoolDistrict.setId(UUID.randomUUID());
                            schoolDistrict.setSchoolType(schoolType);
                            schoolDistrict.setBuilding(building);
                            return schoolDistrict;
                        })
                        .collect(Collectors.toList()))
                .orElse(new ArrayList<>());
        List<SchoolDistrict> schoolDistricts = building.getSchoolDistricts();
        schoolDistricts.clear();
        schoolDistricts.addAll(newSchoolDistricts);

        // 편의시설
        List<FacilityType> facilityTypes = dto.getFacilityTypes();
        List<Facility> newFacilities = Optional.ofNullable(facilityTypes)
                .map(types -> types.stream().map(facilityType -> {
                    Facility facility = new Facility();
                    facility.setId(UUID.randomUUID());
                    facility.setBuilding(building);
                    facility.setFacilityType(facilityType);
                    return facility;
                }).collect(Collectors.toList())).orElse(new ArrayList<>());
        List<Facility> facilities = building.getFacilities();
        facilities.clear();
        facilities.addAll(newFacilities);

        // 교통수단
        List<TransportationType> transportationTypes = dto.getTransportationTypes();
        List<Transportation> newTransportations = Optional.ofNullable(transportationTypes)
                .map(types -> types.stream().map(transportationType -> {
                    Transportation transportation = new Transportation();
                    transportation.setId(UUID.randomUUID());
                    transportation.setTransportationType(transportationType);
                    transportation.setBuilding(building);
                    return transportation;
                }).collect(Collectors.toList())).orElse(new ArrayList<>());
        List<Transportation> transportations = building.getTransportations();
        transportations.clear();
        transportations.addAll(newTransportations);

        return building;
    }

    @Override
    public List<Building> getBuildingsByMemberId(UUID memberId) {
        return buildingRepository.findByMemberId(memberId);
    }
}
