package com.dobot.imjang.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dobot.imjang.entity.Home;

@Repository
public interface JpaHomeRepository extends JpaRepository<Home, UUID>, HomeRepository {

}
