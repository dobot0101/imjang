package com.dobot.imjang.domain.building;

import com.dobot.imjang.domain.common.exception.CustomException;
import com.dobot.imjang.domain.common.exception.ErrorCode;
import com.dobot.imjang.domain.member.Member;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BuildingService {
    private static final String BUILDING_ID_SHOULD_NOT_BE_NULL = "Building id should not be null.";
    private final BuildingRepository buildingRepository;

    public List<Building> getAllBuildings() {
        return buildingRepository.findAll();
    }

    public Building getBuildingById(UUID id) {
        return buildingRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.BUILDING_NOT_FOUND));
    }

    public Building createBuilding(CreateBuildingDto dto, Member member) {
        validateDuplicatedLocation(dto);
        Building building = Building.create(dto, member);
        return buildingRepository.save(building);
    }

    @Transactional
    public Building updateBuilding(UUID id, UpdateBuildingDto dto) {
        validateBuildingId(id);
        Building building = buildingRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.BUILDING_NOT_FOUND));
        building.update(dto);
        return buildingRepository.save(building);
    }

    @Transactional
    public void deleteBuilding(UUID id) {
        validateBuildingId(id);
        this.getBuildingById(id);
        buildingRepository.deleteById(id);
    }

    public List<Building> getBuildingsByMemberId(UUID memberId) {
        return buildingRepository.findByMemberId(memberId);
    }

    public Slice<Building> getBuildingsWithPagination(GetBuildingsWithPaginationDto dto, Pageable pageable) {
        return buildingRepository.findWithCursorPagination(dto.cursor(), dto.createdAt(), pageable);
    }

    private void validateBuildingId(UUID id) {
        if (Objects.isNull(id)) {
            throw new CustomException(ErrorCode.INVALID_INPUT, BUILDING_ID_SHOULD_NOT_BE_NULL);
        }
    }

    private void validateDuplicatedLocation(IBuildingDto dto) {
        double latitude = dto.getLatitude();
        double longitude = dto.getLongitude();

        List<Building> existingBuildings = buildingRepository.findByLatitudeAndLongitude(latitude, longitude);
        if (!existingBuildings.isEmpty()) {
            throw new CustomException(ErrorCode.DUPLICATE_LOCATION, "좌표 중복 에러 [" + latitude + ", " + longitude + "]");
        }
    }
}

