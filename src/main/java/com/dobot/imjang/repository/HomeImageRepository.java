package com.dobot.imjang.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dobot.imjang.entities.UnitImage;

public interface HomeImageRepository extends JpaRepository<UnitImage, UUID> {
}
