package com.dobot.imjang.domain.building;

import java.time.LocalDateTime;
import java.util.UUID;

public record GetBuildingsWithPaginationDto(UUID cursor, LocalDateTime createdAt) {

}
