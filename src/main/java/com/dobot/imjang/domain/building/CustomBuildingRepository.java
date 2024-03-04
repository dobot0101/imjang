package com.dobot.imjang.domain.building;

import java.util.UUID;
import java.util.List;
import java.time.LocalDateTime;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface CustomBuildingRepository {
  public Slice<Building> findWithCursorPagination(UUID cursor, LocalDateTime createdAt, Pageable pageable);
  public List<Building> findWithOffsetPagination(Pageable pageable);
}
