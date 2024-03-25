package com.dobot.imjang.domain.building;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import com.dobot.imjang.domain.building.enums.FacilityType;
import com.dobot.imjang.domain.building.enums.SchoolType;
import com.dobot.imjang.domain.building.enums.TransportationType;
import com.dobot.imjang.domain.common.exception.CustomException;
import com.dobot.imjang.domain.common.exception.ErrorCode;
import com.dobot.imjang.domain.member.Member;

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

    public Building updateBuilding(UUID id, UpdateBuildingDto dto) {
        if (id == null) {
            throw new Error("id is null");
        }
        Building building = buildingRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.BUILDING_NOT_FOUND));

        this.setBuildingInformation(dto, building);

        return buildingRepository.save(building);
    }

    public void deleteBuilding(UUID id) {
        if (id == null) {
            throw new Error("id is null");
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
        List<SchoolType> schoolTypes = dto.getSchoolTypes();
        List<SchoolDistrict> newSchoolDistricts = schoolTypes.stream().map(schoolType -> {
            SchoolDistrict schoolDistrict = new SchoolDistrict();
            schoolDistrict.setId(UUID.randomUUID());
            schoolDistrict.setSchoolType(schoolType);
            schoolDistrict.setBuilding(building);
            return schoolDistrict;
        }).toList();

        building.getSchoolDistricts().clear();
        building.getSchoolDistricts().addAll(newSchoolDistricts);

        // 편의시설
        List<FacilityType> facilityTypes = dto.getFacilityTypes();
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

        // 교통수단
        List<TransportationType> transportationTypes = dto.getTransportationTypes();
        List<Transportation> newTransportationList = transportationTypes.stream().map(transportationType -> {
            Transportation transportation = new Transportation();
            transportation.setId(UUID.randomUUID());
            transportation.setTransportationType(transportationType);
            transportation.setBuilding(building);
            return transportation;
        }).toList();
        List<Transportation> transPortationList = building.getTransportations();
        transPortationList.clear();
        transPortationList.addAll(newTransportationList);
    }

    public List<Building> getBuildingsByMemberId(UUID memberId) {
        return buildingRepository.findByMemberId(memberId);
    }

    public Slice<Building> getBuildingsWithPagination(GetBuildingsWithPaginationDto dto, Pageable pageable) {
        return buildingRepository.findWithCursorPagination(dto.cursor(), dto.createdAt(), pageable);
    }
}
