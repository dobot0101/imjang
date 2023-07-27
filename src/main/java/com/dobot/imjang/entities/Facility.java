package com.dobot.imjang.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.dobot.imjang.enums.FacilityType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Setter;

@Entity
@Setter
public class Facility {
    @Id
    private UUID id;

    @Enumerated(EnumType.STRING)
    FacilityType facilityType;

    @ManyToOne()
    @JoinColumn(name = "building_id")
    private Building building;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    LocalDateTime createdAt;
}
