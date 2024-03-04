package com.dobot.imjang.domain.building;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

@SpringBootTest
public class CustomBuildingRepositoryImplTest {

  @Autowired
  private CustomBuildingRepository customBuildingRepository;

  @Test
  void findWithCursorPagination() {
    UUID cursor = UUID.randomUUID();
    LocalDateTime createdAt = LocalDateTime.now();
    Pageable pageable = PageRequest.of(0, 10);

    Slice<Building> result = customBuildingRepository.findWithCursorPagination(cursor, createdAt, pageable);

    assertEquals(pageable.getPageSize(), result.getContent().size(), "The result size should match the page size");
  }

  @Test
  void findWithOffsetPagination() {
    Pageable pageable = PageRequest.of(0, 10);

    List<Building> result = customBuildingRepository.findWithOffsetPagination(pageable);

    assertEquals(pageable.getPageSize(), result.size(), "The result size should match the page size");
  }
}