package com.dobot.imjang.domain.building;

import java.util.UUID;

import com.dobot.imjang.domain.building.enums.TransportationType;
import com.dobot.imjang.domain.common.entities.BaseTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
// @Setter
@Entity
public class Transportation extends BaseTime {
    @Id
    private UUID id;

    @Getter
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private TransportationType transportationType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "building_id")
    private Building building;
}
