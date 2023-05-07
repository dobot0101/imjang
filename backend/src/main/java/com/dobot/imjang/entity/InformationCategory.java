package com.dobot.imjang.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
public class InformationCategory {
  @Id
  @Type(type = "uuid-char")
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  UUID id;

  @Column(nullable = false)
  String name;

  @ManyToOne
  InformationCategory parentCategory;

  @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL)
  List<InformationCategory> subCategories;

  @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
  List<InformationItem> items;

  @CreationTimestamp
  @Column(nullable = false, updatable = false)
  private LocalDateTime createdDate;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public InformationCategory getParentCategory() {
    return parentCategory;
  }

  public void setParentCategory(InformationCategory parentCategory) {
    this.parentCategory = parentCategory;
  }

  public List<InformationCategory> getSubCategories() {
    return subCategories;
  }

  public void setSubCategories(List<InformationCategory> subCategories) {
    this.subCategories = subCategories;
  }

  public List<InformationItem> getItems() {
    return items;
  }

  public void setItems(List<InformationItem> items) {
    this.items = items;
  }

  public LocalDateTime getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(LocalDateTime createdDate) {
    this.createdDate = createdDate;
  }

}