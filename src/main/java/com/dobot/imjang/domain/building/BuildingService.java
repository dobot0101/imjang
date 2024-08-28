package com.dobot.imjang.domain.building;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import com.dobot.imjang.domain.common.exception.CustomException;
import com.dobot.imjang.domain.common.exception.ErrorCode;
import com.dobot.imjang.domain.member.Member;

import jakarta.transaction.Transactional;

@Service
public class BuildingService {
    private static final String BUILDING_ID_SHOULD_NOT_BE_NULL = "Building id should not be null.";
    private final BuildingRepository buildingRepository;

    public BuildingService(BuildingRepository buildingRepository) {
        this.buildingRepository = buildingRepository;
    }

    public List<Building> getAllBuildings() {
        return buildingRepository.findAll();
    }

    public Building getBuildingById(UUID id) {
        return buildingRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.BUILDING_NOT_FOUND));
    }

    public Building createBuilding(CreateBuildingDto dto, Member member) {
        validateDuplicatedLocation(dto);

        Building building = buildBuilding(dto, member);
        setAdditionalBuildingInformation(dto, building);

        return buildingRepository.save(building);
    }

    @Transactional
    public Building updateBuilding(UUID id, UpdateBuildingDto dto) {
        validateBuildingId(id);

        Building building = buildingRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.BUILDING_NOT_FOUND));
        updateBuildingDetails(building, dto);
        setAdditionalBuildingInformation(dto, building);

        return buildingRepository.save(building);
    }

    @Transactional
    public void deleteBuilding(UUID id) {
        validateBuildingId(id);
        buildingRepository.deleteById(id);
    }

    public List<Building> getBuildingsByMemberId(UUID memberId) {
        return buildingRepository.findByMemberId(memberId);
    }

    public Slice<Building> getBuildingsWithPagination(GetBuildingsWithPaginationDto dto, Pageable pageable) {
        return buildingRepository.findWithCursorPagination(dto.cursor(), dto.createdAt(), pageable);
    }

    private void setAdditionalBuildingInformation(IBuildingDto dto, Building building) {
        setSchoolDistricts(dto, building);
        setFacilities(dto, building);
        setTransportations(dto, building);
    }

    private void setSchoolDistricts(IBuildingDto dto, Building building) {
        Optional.ofNullable(dto.getSchoolTypes()).ifPresent(schoolTypes -> {
            List<SchoolDistrict> newSchoolDistricts = schoolTypes.stream()
                    .map(schoolType -> SchoolDistrict.builder().id(UUID.randomUUID()).schoolType(schoolType).building(building).build())
                    .toList();
            building.getSchoolDistricts().clear();
            building.getSchoolDistricts().addAll(newSchoolDistricts);
        });
    }

    private void setFacilities(IBuildingDto dto, Building building) {
        Optional.ofNullable(dto.getFacilityTypes()).ifPresent(facilityTypes -> {
            List<Facility> newFacilities = facilityTypes.stream()
                    .map(facilityType -> Facility.builder().id(UUID.randomUUID()).facilityType(facilityType).building(building).build())
                    .toList();
            building.getFacilities().clear();
            building.getFacilities().addAll(newFacilities);
        });
    }

    private void setTransportations(IBuildingDto dto, Building building) {
        Optional.ofNullable(dto.getTransportationTypes()).ifPresent(transportationTypes -> {
            List<Transportation> newTransportationList = transportationTypes.stream()
                    .map(transportationType -> Transportation.builder().id(UUID.randomUUID()).building(building).transportationType(transportationType).build())
                    .toList();
            building.getTransportations().clear();
            building.getTransportations().addAll(newTransportationList);
        });
    }

    private void validateDuplicatedLocation(IBuildingDto dto) {
        double latitude = dto.getLatitude();
        double longitude = dto.getLongitude();

        List<Building> existingBuildings = buildingRepository.findByLatitudeAndLongitude(latitude, longitude);
        if (!existingBuildings.isEmpty()) {
            throw new CustomException(ErrorCode.DUPLICATE_LOCATION, "좌표 중복 에러 [" + latitude + ", " + longitude + "]");
        }
    }

    private void validateBuildingId(UUID id) {
        if (Objects.isNull(id)) {
            throw new CustomException(ErrorCode.INVALID_INPUT, BUILDING_ID_SHOULD_NOT_BE_NULL);
        }
    }

    private Building buildBuilding(CreateBuildingDto dto, Member member) {
        return Building.builder()
                .id(UUID.randomUUID())
                .longitude(dto.getLongitude())
                .latitude(dto.getLatitude())
                .member(member)
                .address(dto.getAddress())
                .name(dto.getName())
                .entranceStructure(dto.getEntranceStructure())
                .elevatorStatus(dto.getElevatorStatus())
                .parkingSpace(dto.getParkingSpace())
                .build();
    }

    private void updateBuildingDetails(Building building, UpdateBuildingDto dto) {
        building.setAddress(dto.getAddress());
        building.setName(dto.getName());
        building.setEntranceStructure(dto.getEntranceStructure());
        building.setElevatorStatus(dto.getElevatorStatus());
        building.setParkingSpace(dto.getParkingSpace());
    }
}