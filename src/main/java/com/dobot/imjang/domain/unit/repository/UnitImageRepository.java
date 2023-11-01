package com.dobot.imjang.domain.unit.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dobot.imjang.domain.unit.entity.UnitImage;

public interface UnitImageRepository extends JpaRepository<UnitImage, UUID> {
}
