package com.dobot.imjang.domain.building.entities;

import java.util.UUID;

import com.dobot.imjang.domain.building.enums.SchoolType;
import com.dobot.imjang.domain.common.entities.BaseTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Setter;

@Setter
@Entity
public class SchoolDistrict extends BaseTime {
    @Id
    UUID id;

    @Enumerated(EnumType.STRING)
    SchoolType schoolType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "building_id")
    Building building;
}
