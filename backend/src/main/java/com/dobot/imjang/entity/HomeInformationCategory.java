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

@Entity(name = "home_information_category")
public class HomeInformationCategory {
  @Id
  @Type(type = "uuid-char")
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  UUID id;

  @Column(nullable = false)
  String name;

  @ManyToOne
  HomeInformationCategory parentCategory;

  @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL)
  List<HomeInformationCategory> subCategories;

  @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
  List<HomeInformationItem> items;

  @CreationTimestamp
  @Column(nullable = false, updatable = false)
  private LocalDateTime createdDate;
}