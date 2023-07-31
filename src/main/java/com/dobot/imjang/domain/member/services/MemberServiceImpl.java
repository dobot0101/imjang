package com.dobot.imjang.domain.member.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dobot.imjang.domain.member.dtos.MemberSignUpRequest;
import com.dobot.imjang.domain.member.entities.Member;
import com.dobot.imjang.domain.member.repositories.MemberRepository;

@Service
public class MemberServiceImpl implements MemberService {
  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;

  public MemberServiceImpl(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
    this.memberRepository = memberRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public Member createMember(MemberSignUpRequest memberSignUpRequest) {
    Member savedMember = this.memberRepository.save(memberSignUpRequest.toEntity(this.passwordEncoder));
    return savedMember;
  }
}
