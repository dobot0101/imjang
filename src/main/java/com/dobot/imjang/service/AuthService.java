package com.dobot.imjang.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.dobot.imjang.dtos.KakaoUserInfo;
import com.dobot.imjang.entities.Member;
import com.dobot.imjang.repository.MemberRepository;

@Service
public class AuthService {
  MemberRepository memberRepository;

  public AuthService(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
  }

  public Member signup(KakaoUserInfo kakaoUserInfo) {
    Member member = Member.builder().id(UUID.randomUUID()).nickname(kakaoUserInfo.getNickname())
        .email(kakaoUserInfo.getEmail()).build();

    return this.memberRepository.save(member);
  }

  public String login(Member member) {
    // String email = member.getEmail();

    return "";
  }

}
