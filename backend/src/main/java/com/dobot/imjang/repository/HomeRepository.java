package com.dobot.imjang.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dobot.imjang.entity.Home;

public interface HomeRepository extends JpaRepository<Home, UUID> {
}
