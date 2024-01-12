package com.dobot.imjang.domain.unit;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UnitImageRepository extends JpaRepository<UnitImage, UUID> {
}
