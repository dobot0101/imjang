package com.dobot.imjang.domain.unit.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dobot.imjang.domain.unit.entity.Unit;

public interface UnitRepository extends JpaRepository<Unit, UUID> {

}
