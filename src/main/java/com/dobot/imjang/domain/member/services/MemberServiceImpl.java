package com.dobot.imjang.domain.member.services;

import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dobot.imjang.domain.common.exception.CustomException;
import com.dobot.imjang.domain.common.exception.ErrorCode;
import com.dobot.imjang.domain.member.dtos.SignUpRequestDto;
import com.dobot.imjang.domain.member.entities.Member;
import com.dobot.imjang.domain.member.repositories.MemberRepository;

import jakarta.validation.ValidationException;

@Service
public class MemberServiceImpl implements MemberService {
  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;

  public MemberServiceImpl(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
    this.memberRepository = memberRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public Member createMember(SignUpRequestDto memberSignUpRequest) {
    if (!memberSignUpRequest.getPassword().equals(memberSignUpRequest.getConfirmPassword())) {
      throw new ValidationException("비밀번호를 확인해주세요.");
    }
    Member savedMember = this.memberRepository.save(memberSignUpRequest.toEntity(this.passwordEncoder));
    return savedMember;
  }

  @Override
  public Member getMemberById(UUID id) {
    return this.memberRepository.findById(id)
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND, "회원을 찾을 수 없습니다. 아이디: " + id));
  }
}
