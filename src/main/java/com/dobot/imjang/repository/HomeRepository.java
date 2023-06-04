package com.dobot.imjang.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.dobot.imjang.entity.Home;

// public interface HomeRepository extends JpaRepository<Home, UUID> {
public interface HomeRepository {
  Home save(Home home);

  void deleteById(UUID id);

  Optional<Home> findById(UUID id);

  List<Home> findAll();

}
