package com.dobot.imjang.domain.building;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dobot.imjang.domain.auth.AuthService;
import com.dobot.imjang.domain.auth.CustomUserDetails;
import com.dobot.imjang.domain.member.Member;
import com.dobot.imjang.domain.permission.PermissionChecker;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/buildings")
public class BuildingController {
    private final BuildingService buildingService;
    private final PermissionChecker permissionChecker;
    private final AuthService authService;

    public BuildingController(BuildingService buildingService,
            PermissionChecker permissionChecker, AuthService authService) {
        this.buildingService = buildingService;
        this.permissionChecker = permissionChecker;
        this.authService = authService;
    }

    @GetMapping("")
    public ResponseEntity<Map<String, Object>> getAllBuildings() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        List<Building> buildings = buildingService.getBuildingsByMemberId(userDetails.getId());
        Map<String, Object> map = new HashMap<>();
        map.put("buildings", buildings);
        return ResponseEntity.ok(map);
    }

    public ResponseEntity<Slice<Building>> getBuildingsWithPagination(GetBuildingsWithPaginationDto dto,
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(buildingService.getBuildingsWithPagination(dto, pageable));
    }

    @GetMapping("/{id}")
    public Building getBuilding(@PathVariable("id") UUID id) {
        Building building = buildingService.getBuildingById(id);
        permissionChecker.checkPermission(building.getMember().getId());
        return building;
    }

    @PostMapping("")
    public ResponseEntity<Map<String, String>> createBuilding(
            @RequestBody @Valid CreateBuildingDto createBuildingDto) {
        Member member = authService.getMemberFromAuthenticatedInfo();
        Building building = buildingService.createBuilding(createBuildingDto, member);
        Map<String, String> response = new HashMap<String, String>();
        response.put("savedBuildingId", building.getId().toString());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> updateBuilding(@PathVariable("id") UUID buildingId,
            @RequestBody @Valid UpdateBuildingDto updateBuildingDto) {
        Building building = buildingService.getBuildingById(buildingId);
        permissionChecker.checkPermission(building.getMember().getId());

        Building updatedBuilding = buildingService.updateBuilding(buildingId, updateBuildingDto);
        Map<String, String> response = new HashMap<String, String>();
        response.put("savedBuildingId", updatedBuilding.getId().toString());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteBuilding(@PathVariable("id") UUID buildingId) {
        Building building = buildingService.getBuildingById(buildingId);
        permissionChecker.checkPermission(building.getMember().getId());

        buildingService.deleteBuilding(buildingId);
        Map<String, String> response = new HashMap<>();
        response.put("result", "success");
        return ResponseEntity.ok(response);
    }
}