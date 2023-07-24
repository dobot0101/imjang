package com.dobot.imjang.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

// @Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity()
public class Member {

  @Builder
  public Member(UUID id, String nickname, String email, MemberKakaoLogin kakaoLogin) {
    this.id = id;
    this.nickname = nickname;
    this.email = email;
    this.kakaoLogin = kakaoLogin;
  }

  @Id
  UUID id;

  @Column(length = 20, nullable = true)
  String nickname;

  @Column(length = 50, nullable = false)
  String email;

  @OneToOne(mappedBy = "member")
  MemberKakaoLogin kakaoLogin;

  public MemberKakaoLogin getKakaoLogin() {
    return kakaoLogin;
  }

  @CreationTimestamp
  @Column(nullable = false, updatable = false)
  LocalDateTime createdDate;
}
