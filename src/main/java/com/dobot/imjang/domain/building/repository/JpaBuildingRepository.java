package com.dobot.imjang.domain.building.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dobot.imjang.domain.building.entity.Building;

@Repository
public interface JpaBuildingRepository extends JpaRepository<Building, UUID>, BuildingRepository {
}
