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
        isDuplicatedLocation(dto);

        var building = Building.builder().id(UUID.randomUUID()).longitude(dto.getLongitude())
                .latitude(dto.getLatitude()).member(member).address(dto.getAddress())
                .name(dto.getName())
                .entranceStructure(dto.getEntranceStructure())
                .elevatorStatus(dto.getElevatorStatus())
                .parkingSpace(dto.getParkingSpace()).build();
        this.setAdditionalBuildingInformation(dto, building);

        return buildingRepository.save(building);
    }
    

    @Transactional
    public Building updateBuilding(UUID id, UpdateBuildingDto dto) {
        if (Objects.isNull(id)) {
            throw new CustomException(ErrorCode.INVALID_INPUT, BUILDING_ID_SHOULD_NOT_BE_NULL);
        }
        Building building = buildingRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.BUILDING_NOT_FOUND));
        building.setAddress(dto.getAddress());
        building.setName(dto.getName());
        building.setEntranceStructure(dto.getEntranceStructure());
        building.setElevatorStatus(dto.getElevatorStatus());
        building.setParkingSpace(dto.getParkingSpace());
        this.setAdditionalBuildingInformation(dto, building);
        return buildingRepository.save(building);
    }

    @Transactional
    public void deleteBuilding(UUID id) {
        if (Objects.isNull(id)) {
            throw new CustomException(ErrorCode.INVALID_INPUT, BUILDING_ID_SHOULD_NOT_BE_NULL);
        }
        buildingRepository.deleteById(id);
    }

    

    public List<Building> getBuildingsByMemberId(UUID memberId) {
        return buildingRepository.findByMemberId(memberId);
    }

    public Slice<Building> getBuildingsWithPagination(GetBuildingsWithPaginationDto dto, Pageable pageable) {
        return buildingRepository.findWithCursorPagination(dto.cursor(), dto.createdAt(), pageable);
    }

    private void setAdditionalBuildingInformation(IBuildingDto dto, Building building) {

        // 학군
        Optional.ofNullable(dto.getSchoolTypes()).ifPresent(schoolTypes -> {
            List<SchoolDistrict> newSchoolDistricts = schoolTypes.stream().map(schoolType -> {
                return SchoolDistrict.builder().id(UUID.randomUUID()).schoolType(schoolType).building(building).build();
            }).toList();
            building.getSchoolDistricts().clear();
            building.getSchoolDistricts().addAll(newSchoolDistricts);
        });

        // 편의시설
        Optional.ofNullable(dto.getFacilityTypes()).ifPresent(facilityTypes -> {
            List<Facility> newFacilities = facilityTypes.stream().map(facilityType -> {
                return Facility.builder().id(UUID.randomUUID()).facilityType(facilityType).building(building).build();
            }).toList();
            List<Facility> facilities = building.getFacilities();
            facilities.clear();
            facilities.addAll(newFacilities);
        });

        // 교통수단
        Optional.ofNullable(dto.getTransportationTypes()).ifPresent(transportationTypes -> {
            List<Transportation> newTransportationList = transportationTypes.stream().map(transportationType -> {
                return Transportation.builder().id(UUID.randomUUID()).building(building).transportationType(transportationType).build();
            }).toList();
            List<Transportation> transportationList = building.getTransportations();
            transportationList.clear();
            transportationList.addAll(newTransportationList);
        });
    }

    private void isDuplicatedLocation(IBuildingDto dto) {
        double latitude = dto.getLatitude();
        double longitude = dto.getLongitude();

        List<Building> existingBuildings = this.buildingRepository.findByLatitudeAndLongitude(latitude, longitude);
        if (!existingBuildings.isEmpty()) {
            throw new CustomException(ErrorCode.DUPLICATE_LOCATION, "좌표 중복 에러 [" + latitude + ", " + longitude + "]");
        }
    }
}
