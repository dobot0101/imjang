package com.dobot.imjang.domain.building;

import java.util.List;
import java.util.UUID;
import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomBuildingRepositoryImpl implements CustomBuildingRepository {
    private final JPAQueryFactory query;

    @Override
    public Slice<Building> findWithCursorPagination(UUID cursor, LocalDateTime createdAt, Pageable pageable) {
        QBuilding qBuilding = QBuilding.building;
        List<Building> content = query.select(qBuilding).from(qBuilding)
                .where(qBuilding.createdAt.eq(createdAt).and(qBuilding.id.lt(cursor))
                        .or(qBuilding.createdAt.lt(createdAt)))
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
    public Page<Building> findWithOffsetPagination(Pageable pageable) {
        var qBuilding = QBuilding.building;
        var content = query.select(qBuilding).from(qBuilding)
                .orderBy(qBuilding.createdAt.desc(), qBuilding.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        var count = query.select(qBuilding.count()).from(qBuilding).fetchFirst();
        return new PageImpl<>(content, pageable, count);
    }

}
