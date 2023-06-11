package com.dobot.imjang.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dobot.imjang.entities.Unit;

public interface UnitRepository extends JpaRepository<Unit, UUID> {

}
