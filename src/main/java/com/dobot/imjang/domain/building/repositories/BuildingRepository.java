package com.dobot.imjang.domain.building.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.dobot.imjang.domain.building.entities.Building;
import com.dobot.imjang.domain.member.entities.Member;

public interface BuildingRepository {
  Building save(Building home);

  void deleteById(UUID id);

  Optional<Building> findById(UUID id);

  List<Building> findAll();

  List<Building> findByLatitudeAndLongitude(double latitude, double longitude);

  List<Building> findByMember(Member member);
}
