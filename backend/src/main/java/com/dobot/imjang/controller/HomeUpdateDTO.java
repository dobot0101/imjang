package com.dobot.imjang.controller;

import java.util.List;
import java.util.UUID;

public class HomeUpdateDTO {
  private String name;
  private String address;
  private String memo;
  private String area;
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

  public String getArea() {
    return area;
  }

  public void setArea(String area) {
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

}
