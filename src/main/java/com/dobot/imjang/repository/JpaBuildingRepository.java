package com.dobot.imjang.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dobot.imjang.entities.Building;

@Repository
public interface JpaBuildingRepository extends JpaRepository<Building, UUID>, BuildingRepository {

}
