package com.dobot.imjang.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
public class Home {
  @Id
  @Type(type = "uuid-char")
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  UUID id;

  @Column(nullable = false)
  String name;

  @Column(nullable = false)
  String address;

  @Column()
  String memo;

  @Column()
  Integer area;

  @OneToMany(mappedBy = "home", cascade = CascadeType.ALL)
  List<HomeInformationItem> homeInformationItems;

  @OneToMany(mappedBy = "home", cascade = CascadeType.ALL)
  List<HomeImage> images;

  @CreationTimestamp
  @Column(nullable = false, updatable = false)
  private LocalDateTime createdDate;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public void setMemo(String memo) {
    this.memo = memo;
  }

  public void setArea(Integer area) {
    this.area = area;
  }

  public UUID getId() {
    return id;
  }

  public String getAddress() {
    return address;
  }

  public String getMemo() {
    return memo;
  }

  public Integer getArea() {
    return area;
  }
}
