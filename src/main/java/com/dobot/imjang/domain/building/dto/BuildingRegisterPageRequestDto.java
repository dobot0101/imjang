package com.dobot.imjang.domain.building.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BuildingRegisterPageRequestDto {
  private double latitude;
  private double longitude;
  private String address;
  private String buildingName;
}
