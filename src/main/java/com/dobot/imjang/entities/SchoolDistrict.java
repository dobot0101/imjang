package com.dobot.imjang.entities;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
}
