package com.dobot.imjang.domain.building;

import java.util.UUID;
import java.time.LocalDateTime;

public record GetBuildingsWithPaginationDto(UUID cursor, LocalDateTime createdAt) {

}
