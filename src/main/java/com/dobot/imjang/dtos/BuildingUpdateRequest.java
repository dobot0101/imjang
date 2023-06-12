package com.dobot.imjang.dtos;

import java.util.List;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BuildingUpdateRequest {
  private String name;
  private String address;
  private String memo;
  private Integer area;
  private List<UUID> imageIds;
  private List<UUID> informationItemIds;

}
