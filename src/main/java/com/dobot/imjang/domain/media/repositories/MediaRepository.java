package com.dobot.imjang.domain.media.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dobot.imjang.domain.media.entities.Media;

public interface MediaRepository extends JpaRepository<Media, UUID> {

}
