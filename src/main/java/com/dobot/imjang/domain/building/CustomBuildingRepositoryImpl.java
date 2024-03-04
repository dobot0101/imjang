package com.dobot.imjang.domain.building;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import com.querydsl.jpa.impl.JPAQuery;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomBuildingRepositoryImpl implements CustomBuildingRepository {
  private final JPAQuery<Building> query;

  @Override
  public Slice<Building> findWithCursorPagination(UUID cursor, LocalDateTime createdAt, Pageable pageable) {
    var qBuilding = QBuilding.building;
    List<Building> content = query.select(qBuilding).from(qBuilding)
        .where(qBuilding.createdAt.lt(createdAt).or(qBuilding.createdAt.eq(createdAt).and(qBuilding.id.lt(cursor))))
        .orderBy(qBuilding.createdAt.desc(), qBuilding.id.desc())
        .limit(pageable.getPageSize() + 1)
        .fetch();

    boolean hasNext = false;
    if (content.size() > pageable.getPageSize()) {
      hasNext = true;
      content.remove(pageable.getPageSize());
    }

    return new SliceImpl<>(content, pageable, hasNext);
  }

  @Override
  public List<Building> findWithOffsetPagination(Pageable pageable) {
    var qBuilding = QBuilding.building;
    return query.select(qBuilding).from(qBuilding)
        .orderBy(qBuilding.createdAt.desc(), qBuilding.id.desc())
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();
  }

}
