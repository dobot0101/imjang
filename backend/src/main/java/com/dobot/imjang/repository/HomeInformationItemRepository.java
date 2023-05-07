package com.dobot.imjang.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dobot.imjang.entity.InformationItem;

public interface HomeInformationItemRepository extends JpaRepository<InformationItem, UUID> {

}
