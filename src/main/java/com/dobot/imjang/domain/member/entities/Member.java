package com.dobot.imjang.domain.member.entities;

import java.util.List;
import java.util.UUID;

import com.dobot.imjang.domain.building.entities.Building;
import com.dobot.imjang.domain.common.entities.BaseTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity()
public class Member extends BaseTime {

  @Builder
  public Member(UUID id, String email, String password, String name) {
    this.id = id;
    this.email = email;
    this.password = password;
    this.name = name;
  }

  @Getter
  @Id
  UUID id;

  @Getter
  @Column(length = 50, nullable = false)
  String email;

  @Getter
  @Column(length = 20, nullable = false)
  String name;

  @Getter
  @Column(length = 100, nullable = false)
  String password;

  @OneToMany(mappedBy = "member", orphanRemoval = true, cascade = CascadeType.REMOVE)
  List<Building> buildings;
}
