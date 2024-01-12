package com.dobot.imjang.domain.upload;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UploadResultRepository extends JpaRepository<UploadResult, UUID> {

}
