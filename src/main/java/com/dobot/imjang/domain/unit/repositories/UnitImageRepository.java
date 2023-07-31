package com.dobot.imjang.domain.unit.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dobot.imjang.domain.unit.entities.UnitImage;

public interface UnitImageRepository extends JpaRepository<UnitImage, UUID> {
}
