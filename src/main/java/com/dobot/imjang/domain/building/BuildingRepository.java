package com.dobot.imjang.domain.building;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BuildingRepository extends JpaRepository<Building, UUID>, CustomBuildingRepository {

  List<Building> findByLatitudeAndLongitude(double latitude, double longitude);

  List<Building> findByMemberId(UUID memberId);
}
