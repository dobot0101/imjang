package com.dobot.imjang.entities;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.Builder;
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

  @Id
  UUID id;

  @Column(length = 50, nullable = false)
  String email;

  @Column(length = 20, nullable = false)
  String password;
}
