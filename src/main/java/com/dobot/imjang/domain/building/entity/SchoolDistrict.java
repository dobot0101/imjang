package com.dobot.imjang.domain.building.entity;

import java.util.UUID;

import com.dobot.imjang.domain.building.enums.SchoolType;
import com.dobot.imjang.domain.common.entities.BaseTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Setter
@Entity
public class SchoolDistrict extends BaseTime {
    @Id
    private UUID id;

    @Getter
    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private SchoolType schoolType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "building_id")
    private Building building;
}
