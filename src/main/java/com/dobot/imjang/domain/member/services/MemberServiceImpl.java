package com.dobot.imjang.domain.member.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dobot.imjang.domain.common.exceptions.NotFoundException;
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

  @Override
  public Member getMemberById(UUID id) {
    return this.memberRepository.findById(id.toString())
        .orElseThrow(() -> new NotFoundException("회원을 찾을 수 없습니다. 아이디: " + id));
  }
}
