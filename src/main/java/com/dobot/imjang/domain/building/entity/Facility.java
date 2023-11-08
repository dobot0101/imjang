package com.dobot.imjang.domain.building.entity;

import java.util.UUID;

import com.dobot.imjang.domain.building.enums.FacilityType;
import com.dobot.imjang.domain.common.entities.BaseTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
public class Facility extends BaseTime {
    @Id
    private UUID id;

    @Getter
    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private FacilityType facilityType;

    @ManyToOne()
    @JoinColumn(name = "building_id")
    private Building building;
}
