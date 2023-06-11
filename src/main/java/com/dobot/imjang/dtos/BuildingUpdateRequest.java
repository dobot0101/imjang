package com.dobot.imjang.dtos;

import java.util.List;
import java.util.UUID;

import com.dobot.imjang.entities.Facility;
import com.dobot.imjang.entities.Transportation;
import com.dobot.imjang.entities.Unit;

public class BuildingUpdateRequest {
  private String name;
  private String address;
  private String memo;
  private Integer area;
  private List<UUID> imageIds;
  private List<UUID> informationItemIds;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getMemo() {
    return memo;
  }

  public void setMemo(String memo) {
    this.memo = memo;
  }

  public Integer getArea() {
    return area;
  }

  public void setArea(Integer area) {
    this.area = area;
  }

  public List<UUID> getImageIds() {
    return imageIds;
  }

  public void setImageIds(List<UUID> imageIds) {
    this.imageIds = imageIds;
  }

  public List<UUID> getInformationItemIds() {
    return informationItemIds;
  }

  public void setInformationItemIds(List<UUID> informationItemIds) {
    this.informationItemIds = informationItemIds;
  }

  public List<Unit> getUnits() {
    return null;
  }

  public List<Facility> getFacilities() {
    return null;
  }

  public List<Transportation> getTransportations() {
    return null;
  }

}
