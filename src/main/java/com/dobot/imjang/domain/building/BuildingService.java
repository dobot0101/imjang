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
        Building building = new Building();
        building.setId(UUID.randomUUID());
        building.setLongitude(dto.getLongitude());
        building.setLatitude(dto.getLatitude());
        building.setMember(member);

        setBuildingInformation(dto, building);

        return buildingRepository.save(building);
    }

    private void isDuplicatedLocation(IBuildingDto dto) {
        double latitude = dto.getLatitude();
        double longitude = dto.getLongitude();

        List<Building> existingBuildings = this.buildingRepository.findByLatitudeAndLongitude(latitude, longitude);
        if (!existingBuildings.isEmpty()) {
            throw new CustomException(ErrorCode.DUPLICATE_LOCATION, "좌표 중복 에러 [" + latitude + ", " + longitude + "]");
        }
    }

    @Transactional
    public Building updateBuilding(UUID id, UpdateBuildingDto dto) {
        if (Objects.isNull(id)) {
            throw new CustomException(ErrorCode.INVALID_INPUT, BUILDING_ID_SHOULD_NOT_BE_NULL);
        }
        Building building = buildingRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.BUILDING_NOT_FOUND));
        this.setBuildingInformation(dto, building);
        return buildingRepository.save(building);
    }

    @Transactional
    public void deleteBuilding(UUID id) {
        if (Objects.isNull(id)) {
            throw new CustomException(ErrorCode.INVALID_INPUT, BUILDING_ID_SHOULD_NOT_BE_NULL);
        }
        buildingRepository.deleteById(id);
    }

    private void setBuildingInformation(IBuildingDto dto, Building building) {
        building.setAddress(dto.getAddress());
        building.setName(dto.getName());
        building.setEntranceStructure(dto.getEntranceStructure());
        building.setElevatorStatus(dto.getElevatorStatus());
        building.setParkingSpace(dto.getParkingSpace());

        // 학군
        Optional.ofNullable(dto.getSchoolTypes()).ifPresent(schoolTypes -> {
            List<SchoolDistrict> newSchoolDistricts = schoolTypes.stream().map(schoolType -> {
                SchoolDistrict schoolDistrict = new SchoolDistrict();
                schoolDistrict.setId(UUID.randomUUID());
                schoolDistrict.setSchoolType(schoolType);
                schoolDistrict.setBuilding(building);
                return schoolDistrict;
            }).toList();
            building.getSchoolDistricts().clear();
            building.getSchoolDistricts().addAll(newSchoolDistricts);
        });

        // 편의시설
        Optional.ofNullable(dto.getFacilityTypes()).ifPresent(facilityTypes -> {
            List<Facility> newFacilities = facilityTypes.stream().map(facilityType -> {
                Facility facility = new Facility();
                facility.setId(UUID.randomUUID());
                facility.setBuilding(building);
                facility.setFacilityType(facilityType);
                return facility;
            }).toList();
            List<Facility> facilities = building.getFacilities();
            facilities.clear();
            facilities.addAll(newFacilities);
        });

        // 교통수단
        Optional.ofNullable(dto.getTransportationTypes()).ifPresent(transportationTypes -> {
            List<Transportation> newTransportationList = transportationTypes.stream().map(transportationType -> {
                Transportation transportation = new Transportation();
                transportation.setId(UUID.randomUUID());
                transportation.setBuilding(building);
                transportation.setTransportationType(transportationType);
                return transportation;
            }).toList();
            List<Transportation> transportationList = building.getTransportations();
            transportationList.clear();
            transportationList.addAll(newTransportationList);
        });
    }

    public List<Building> getBuildingsByMemberId(UUID memberId) {
        return buildingRepository.findByMemberId(memberId);
    }

    public Slice<Building> getBuildingsWithPagination(GetBuildingsWithPaginationDto dto, Pageable pageable) {
        return buildingRepository.findWithCursorPagination(dto.cursor(), dto.createdAt(), pageable);
    }
}
