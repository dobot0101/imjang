package com.dobot.imjang.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dobot.imjang.entity.InformationItem;

@Repository
public interface InformationItemRepository extends JpaRepository<InformationItem, UUID> {

}
