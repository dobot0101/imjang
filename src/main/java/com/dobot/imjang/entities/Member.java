package com.dobot.imjang.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.hibernate.annotations.CreationTimestamp;

@Entity()
public class Member {

  @Id
  UUID id;

  @Column(length = 20, nullable = false)
  String name;

  @Column(length = 50, nullable = false)
  String email;

  @OneToOne(mappedBy = "member")
  MemberKakaoLogin kakaoLogin;

  public MemberKakaoLogin getKakaoLogin() {
    return kakaoLogin;
  }

  @CreationTimestamp
  @Column(nullable = false, updatable = false)
  private LocalDateTime createdDate;
}
