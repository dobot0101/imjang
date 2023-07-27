package com.dobot.imjang.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import com.dobot.imjang.enums.SchoolType;

import lombok.Setter;

@Setter
@Entity
public class SchoolDistrict {
    @Id
    UUID id;

    @Enumerated(EnumType.STRING)
    SchoolType schoolType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "building_id")
    Building building;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    LocalDateTime createdDate;
}
