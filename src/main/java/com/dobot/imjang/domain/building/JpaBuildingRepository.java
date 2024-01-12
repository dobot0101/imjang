package com.dobot.imjang.domain.building;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaBuildingRepository extends JpaRepository<Building, UUID>, BuildingRepository {
}
