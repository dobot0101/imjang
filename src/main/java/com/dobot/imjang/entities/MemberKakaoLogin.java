package com.dobot.imjang.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.hibernate.annotations.CreationTimestamp;

@Entity()
public class MemberKakaoLogin {
  @Id
  String id;

  @Column(nullable = false, unique = true)
  String kakaoUserId;

  @OneToOne
  Member member;

  public Member getMember() {
    return member;
  }

  public String getKakaoUserId() {
    return kakaoUserId;
  }

  @CreationTimestamp
  @Column(nullable = false, updatable = false)
  private LocalDateTime createdDate;
}