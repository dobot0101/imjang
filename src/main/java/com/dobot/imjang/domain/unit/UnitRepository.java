package com.dobot.imjang.domain.unit;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UnitRepository extends JpaRepository<Unit, UUID> {

}
