package com.dobot.imjang.domain.unit.entity;

import java.util.UUID;

import com.dobot.imjang.domain.common.entities.BaseTime;
import com.dobot.imjang.domain.upload.entity.UploadResult;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class UnitImage extends BaseTime {
  @Id
  UUID id;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "upload_result_id")
  UploadResult uploadResult;

  @JsonBackReference
  @ManyToOne
  @JoinColumn(name = "unit_id")
  Unit unit;
}