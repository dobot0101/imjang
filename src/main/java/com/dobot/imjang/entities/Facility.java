package com.dobot.imjang.entities;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.dobot.imjang.enums.FacilityType;

@Entity
public class Facility {

    @Id
    private Long id;

    @Enumerated(EnumType.STRING)
    FacilityType facilityType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "building_id")
    private Building building;

    public void setId(Long id) {
        this.id = id;
    }

    public void setFacilityType(FacilityType facilityType) {
        this.facilityType = facilityType;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    // Getters and setters...

}
