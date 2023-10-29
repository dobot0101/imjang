package com.dobot.imjang.domain.building.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dobot.imjang.domain.building.entities.Building;

@Repository
public interface JpaBuildingRepository extends JpaRepository<Building, UUID>, BuildingRepository {
}
