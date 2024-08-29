package com.dobot.imjang.domain.building;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.time.LocalDateTime;
import java.util.UUID;

public interface CustomBuildingRepository {
    Slice<Building> findWithCursorPagination(UUID cursor, LocalDateTime createdAt, Pageable pageable);

    Page<Building> findWithOffsetPagination(Pageable pageable);
}
