package com.dobot.imjang.domain.upload.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dobot.imjang.domain.upload.entity.UploadResult;

public interface UploadResultRepository extends JpaRepository<UploadResult, UUID> {

}
