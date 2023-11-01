package com.dobot.imjang.domain.building.dto;

import lombok.Getter;

@Getter
public class BuildingRegisterPageRequestDto {
  double latitude;
  double longitude;
  String address;
  String buildingName;
}
