package com.dobot.imjang.domain.building;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BuildingRepository {
  Building save(Building home);

  void deleteById(UUID id);

  Optional<Building> findById(UUID id);

  List<Building> findAll();

  List<Building> findByLatitudeAndLongitude(double latitude, double longitude);

  List<Building> findByMemberId(UUID memberId);
}
