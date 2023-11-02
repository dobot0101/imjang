package com.dobot.imjang.domain.building.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BuildingRegisterPageRequestDto {
  double latitude;
  double longitude;
  String address;
  String buildingName;
}
