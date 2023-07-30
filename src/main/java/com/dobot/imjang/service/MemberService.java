package com.dobot.imjang.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dobot.imjang.dtos.MemberSignUpRequest;
import com.dobot.imjang.entities.Member;
import com.dobot.imjang.repository.MemberRepository;

@Service
public class MemberService {
  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;

  public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
    this.memberRepository = memberRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public Member createMember(MemberSignUpRequest memberSignUpRequest) {
    Member savedMember = this.memberRepository.save(memberSignUpRequest.toEntity(this.passwordEncoder));
    return savedMember;
  }
}
