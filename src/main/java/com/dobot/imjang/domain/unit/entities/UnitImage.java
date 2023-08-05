package com.dobot.imjang.domain.unit.entities;

import java.util.UUID;

import com.dobot.imjang.domain.common.entities.BaseTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Setter;

@Setter
@Entity
public class UnitImage extends BaseTime {
  @Id
  UUID id;

  @Column(nullable = false)
  String imageUrl;

  @ManyToOne
  Unit unit;
}