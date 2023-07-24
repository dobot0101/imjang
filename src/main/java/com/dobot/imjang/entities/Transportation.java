package com.dobot.imjang.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import com.dobot.imjang.enums.TransportationType;

import lombok.Setter;

@Setter
@Entity
public class Transportation {
    @Id
    private UUID id;

    @Enumerated(EnumType.STRING)
    private TransportationType transportationType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "building_id")
    private Building building;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    LocalDateTime createdDate;
}
