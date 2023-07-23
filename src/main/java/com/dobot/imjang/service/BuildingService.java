package com.dobot.imjang.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.dobot.imjang.dtos.CreateBuildingRequest;
import com.dobot.imjang.entities.Building;
import com.dobot.imjang.entities.Facility;
import com.dobot.imjang.entities.SchoolDistrict;
import com.dobot.imjang.entities.Transportation;
import com.dobot.imjang.entities.Unit;
import com.dobot.imjang.entities.UnitImage;
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

    public Building createBuilding(CreateBuildingRequest request) {
        Building building = new Building();
        building.setId(UUID.randomUUID());
        setBuildingInformations(request, building);

        return buildingRepository.save(building);
    }

    public Building updateBuilding(UUID id, CreateBuildingRequest req) {
        Optional<Building> optional = buildingRepository.findById(id);
        if (!optional.isPresent()) {
            throw new NotFoundException("Building not found");
        }

        Building building = optional.get();
        this.setBuildingInformations(req, building);

        return buildingRepository.save(building);
    }

    public void deleteBuilding(UUID id) {
        buildingRepository.deleteById(id);
    }

    private Building setBuildingInformations(CreateBuildingRequest request, Building building) {
        building.setAddress(request.getAddress());
        building.setName(request.getName());
        building.setEntranceStructure(request.getEntranceStructure());
        building.setElevatorStatus(request.getElevatorStatus());
        building.setLatitude(request.getLatitude());
        building.setLongitude(request.getLongitude());
        building.setParkingSpace(request.getParkingSpace());
        building.setCreatedDate(LocalDateTime.now());

        // 학군
        List<SchoolType> schoolTypes = request.getSchoolTypes();
        if (schoolTypes != null) {
            List<SchoolDistrict> schoolDistricts = schoolTypes.stream().map(schoolType -> {
                SchoolDistrict schoolDistrict = new SchoolDistrict();
                schoolDistrict.setSchoolType(schoolType);
                schoolDistrict.setId(UUID.randomUUID());
                return schoolDistrict;
            }).collect(Collectors.toList());
            building.setSchoolDistricts(schoolDistricts);
        }

        // 편의시설
        List<FacilityType> facilityTypes = request.getFacilityTypes();
        if (facilityTypes != null) {
            building.setFacilities(facilityTypes.stream().map(facilityType -> {
                Facility facility = new Facility();
                facility.setFacilityType(facilityType);
                return facility;
            }).collect(Collectors.toList()));
        }

        // 교통수단
        List<TransportationType> transportationTypes = request.getTransportationTypes();
        if (transportationTypes != null) {
            List<Transportation> transportations = transportationTypes.stream().map(transportationType -> {
                Transportation transportation = new Transportation();
                transportation.setTransportationType(transportationType);
                return transportation;
            }).collect(Collectors.toList());

            building.setTransportations(transportations);
        }

        Unit unit = new Unit();
        unit.setId(UUID.randomUUID());
        unit.setArea(request.getArea());
        unit.setBuildingNumber(request.getBuildingNumber());
        unit.setCondensationMoldLevel(request.getCondensationMoldLevel());
        unit.setDeposit(request.getDeposit());
        unit.setDirection(request.getDirection());
        unit.setLeakStatus(request.getLeakStatus());
        unit.setMemo(request.getMemo());
        unit.setNoiseLevel(request.getNoiseLevel());
        unit.setRoomNumber(request.getRoomNumber());
        unit.setTransactionPrice(request.getTransactionPrice());
        unit.setTransactionType(request.getTransactionType());
        unit.setVentilation(request.getVentilation());
        unit.setViewQuality(request.getViewQuality());
        unit.setWaterPressure(request.getWaterPressure());

        List<String> imageUrls = request.getImageUrls();
        if (imageUrls != null) {
            List<UnitImage> unitImages = imageUrls.stream().map(imageUrl -> {
                UnitImage unitImage = new UnitImage();
                unitImage.setId(UUID.randomUUID());
                unitImage.setImageUrl(imageUrl);
                return unitImage;
            }).collect(Collectors.toList());
            unit.setImages(unitImages);
        }

        List<Unit> units = building.getUnits();
        if (units != null) {
            units.add(unit);
        }

        building.setUnits(units);

        return building;
    }
}
