package com.dobot.imjang.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity()
public class MemberKakaoLogin {
  @Id
  @Type(type = "uuid-char")
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  String id;

  @Column(nullable = false, unique = true)
  String kakaoUserId;

  public String getKakaoUserId() {
    return kakaoUserId;
  }

  // String nickname;
  // String profileImageUrl;
  // String email;
  // String phoneNumber;
  @CreationTimestamp
  @Column(nullable = false, updatable = false)
  private LocalDateTime createdDate;
}