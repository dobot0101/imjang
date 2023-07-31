package com.dobot.imjang.domain.unit.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dobot.imjang.domain.unit.entities.Unit;

public interface UnitRepository extends JpaRepository<Unit, UUID> {

}
