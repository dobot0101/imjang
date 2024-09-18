package com.dobot.imjang.domain.building;

import com.dobot.imjang.domain.common.exception.ValidationError;
import com.dobot.imjang.domain.common.exception.ErrorCode;
import com.dobot.imjang.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BuildingService {
    private static final String BUILDING_ID_SHOULD_NOT_BE_NULL = "Building id should not be null.";
    private final BuildingRepository buildingRepository;

    @Transactional(readOnly = true)
    public List<Building> getAllBuildings() {
        return buildingRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Building getBuildingById(UUID id) {
        return buildingRepository.findById(id).orElseThrow(() -> new ValidationError(ErrorCode.BUILDING_NOT_FOUND));
    }

    @Transactional
    public Building createBuilding(CreateBuildingDto dto, Member member) {
        validateDuplicatedLocation(dto);
        Building building = Building.create(dto, member);
        return buildingRepository.save(building);
    }

    @Transactional
    public Building updateBuilding(UUID id, UpdateBuildingDto dto) {
        validateBuildingId(id);
        Building building = buildingRepository.findById(id)
                .orElseThrow(() -> new ValidationError(ErrorCode.BUILDING_NOT_FOUND));
        building.update(dto);
        return buildingRepository.save(building);
    }

    @Transactional
    public void deleteBuilding(UUID id) {
        validateBuildingId(id);
        this.getBuildingById(id);
        buildingRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Building> getBuildingsByMemberId(UUID memberId) {
        return buildingRepository.findByMemberId(memberId);
    }

    @Transactional(readOnly = true)
    public Slice<Building> getBuildingsWithPagination(GetBuildingsWithPaginationDto dto, Pageable pageable) {
        return buildingRepository.findWithCursorPagination(dto.cursor(), dto.createdAt(), pageable);
    }

    private void validateBuildingId(UUID id) {
        if (Objects.isNull(id)) {
            throw new ValidationError(ErrorCode.BAD_REQUEST, BUILDING_ID_SHOULD_NOT_BE_NULL);
        }
    }

    private void validateDuplicatedLocation(IBuildingDto dto) {
        double latitude = dto.getLatitude();
        double longitude = dto.getLongitude();

        List<Building> existingBuildings = buildingRepository.findByLatitudeAndLongitude(latitude, longitude);
        if (!existingBuildings.isEmpty()) {
            throw new ValidationError(ErrorCode.DUPLICATE_LOCATION, "좌표 중복 에러 [" + latitude + ", " + longitude + "]");
        }
    }
}

