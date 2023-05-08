package com.dobot.imjang.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dobot.imjang.entity.InformationCategory;

public interface InformationCategoryRepository extends JpaRepository<InformationCategory, UUID> {

}
