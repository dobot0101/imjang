package com.dobot.imjang.domain.building.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.dobot.imjang.domain.building.entity.Building;

public interface BuildingRepository {
  Building save(Building home);

  void deleteById(UUID id);

  Optional<Building> findById(UUID id);

  List<Building> findAll();

  List<Building> findByLatitudeAndLongitude(double latitude, double longitude);

  List<Building> findByMemberId(UUID memberId);
}