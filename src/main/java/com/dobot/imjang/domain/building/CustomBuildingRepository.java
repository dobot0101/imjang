package com.dobot.imjang.domain.building;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface CustomBuildingRepository {
  public Slice<Building> findWithCursorPagination(UUID cursor, LocalDateTime createdAt, Pageable pageable);
  public Page<Building> findWithOffsetPagination(Pageable pageable);
}
