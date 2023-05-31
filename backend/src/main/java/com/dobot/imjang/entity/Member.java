package com.dobot.imjang.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity()
public class Member {

  @Id
  @Type(type = "uuid-char")
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  UUID id;

  @Column(length = 20, nullable = false)
  String name;

  @Column(length = 50, nullable = false)
  String email;

  @OneToOne(fetch = FetchType.EAGER)
  MemberKakaoLogin kakaoLogin;

  public MemberKakaoLogin getKakaoLogin() {
    return kakaoLogin;
  }

  @CreationTimestamp
  @Column(nullable = false, updatable = false)
  private LocalDateTime createdDate;

}
