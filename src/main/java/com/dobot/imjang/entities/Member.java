package com.dobot.imjang.entities;

import java.util.UUID;

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
  public Member(UUID id, String email, String password) {
    this.id = id;
    this.email = email;
    this.password = password;
  }

  @Getter
  @Id
  UUID id;

  @Column(length = 50, nullable = false)
  String email;

  @Getter
  @Column(length = 100, nullable = false)
  String password;
}
