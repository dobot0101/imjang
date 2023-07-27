package com.dobot.imjang.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.dobot.imjang.dtos.MemberSignUpRequest;
import com.dobot.imjang.entities.Member;
import com.dobot.imjang.repository.MemberRepository;

@Service
public class AuthService {
  MemberRepository memberRepository;

  public AuthService(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
  }

  public Member signup(MemberSignUpRequest request) {
    Member member = Member.builder().id(UUID.randomUUID())
        .email(request.getEmail()).password(request.getPassword()).build();

    return this.memberRepository.save(member);
  }

  public String login(MemberLoginRequest request) {
    return this.memberRepository.findByEmail(request.getEmail());
  }

}
