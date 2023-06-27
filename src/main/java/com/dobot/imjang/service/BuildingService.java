package com.dobot.imjang.service;

import java.time.LocalDateTime;
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

    public Building createBuilding(BuildingRequest req) {
        Building building = new Building();
        setBuildingInformations(req, building);

        return buildingRepository.save(building);
    }

    public Building updateBuilding(UUID id, BuildingRequest req) {
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

    private void setBuildingInformations(BuildingRequest req, Building building) {
        building.setAddress(req.getAddress());
        building.setName(req.getName());
        building.setEntranceStructure(req.getEntranceStructure());
        building.setElevatorStatus(req.getElevatorStatus());
        building.setLatitude(req.getLatitude());
        building.setLongitude(req.getLongitude());
        building.setParkingSpace(req.getParkingSpace());
        building.setCreatedDate(LocalDateTime.now());

        // 학군
        List<SchoolType> schoolTypes = req.getSchoolTypes();
        if (!schoolTypes.isEmpty()) {
            List<SchoolDistrict> schoolDistricts = schoolTypes.stream().map(schoolType -> {
                SchoolDistrict schoolDistrict = new SchoolDistrict();
                schoolDistrict.setSchoolType(schoolType);
                schoolDistrict.setId(UUID.randomUUID());
                return schoolDistrict;
            }).collect(Collectors.toList());
            building.setSchoolDistricts(schoolDistricts);
        }

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

        Unit unit = new Unit();
        unit.setId(UUID.randomUUID());
        unit.setArea(req.getArea());
        unit.setBuildingNumber(req.getBuildingNumber());
        unit.setCondensationMoldLevel(req.getCondensationMoldLevel());
        unit.setDeposit(req.getDeposit());
        unit.setDirection(req.getDirection());
        unit.setLeakStatus(req.getLeakStatus());
        unit.setMemo(req.getMemo());
        unit.setNoiseLevel(req.getNoiseLevel());
        unit.setRoomNumber(req.getRoomNumber());
        unit.setTransactionPrice(req.getTransactionPrice());
        unit.setTransactionType(req.getTransactionType());
        unit.setVentilation(req.getVentilation());
        unit.setViewQuality(req.getViewQuality());
        unit.setWaterPressure(req.getWaterPressure());

        List<String> imageUrls = req.getImageUrls();
        if (!imageUrls.isEmpty()) {
            List<UnitImage> unitImages = imageUrls.stream().map(imageUrl -> {
                UnitImage unitImage = new UnitImage();
                unitImage.setId(UUID.randomUUID());
                unitImage.setImageUrl(imageUrl);
                return unitImage;
            }).collect(Collectors.toList());
            unit.setImages(unitImages);
        }

        List<Unit> units = building.getUnits();
        units.add(unit);
        building.setUnits(units);
    }
}
