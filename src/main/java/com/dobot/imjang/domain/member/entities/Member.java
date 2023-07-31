package com.dobot.imjang.domain.member.entities;

import java.util.UUID;

import com.dobot.imjang.domain.common.entities.BaseTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

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

  @Column(length = 50, nullable = false)
  String email;

  @Getter
  @Column(length = 20, nullable = false)
  String name;

  @Getter
  @Column(length = 100, nullable = false)
  String password;
}
