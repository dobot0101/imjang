package com.dobot.imjang.domain.member.services;

import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dobot.imjang.domain.common.exception.CustomException;
import com.dobot.imjang.domain.common.exception.ErrorCode;
import com.dobot.imjang.domain.member.dtos.SignUpRequestDto;
import com.dobot.imjang.domain.member.entities.Member;
import com.dobot.imjang.domain.member.respositories.MemberRepository;


@Service
public class MemberServiceImpl implements MemberService {
  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;

  public MemberServiceImpl(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
    this.memberRepository = memberRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public Member signUp(SignUpRequestDto signUpRequestDto) throws Exception {
    if (memberRepository.findByEmail(signUpRequestDto.getEmail()).isPresent()) {
      throw new Exception("이미 존재하는 이메일입니다.");
    }

    if (!signUpRequestDto.getPassword().equals(signUpRequestDto.getConfirmPassword())) {
      throw new Exception("비밀번호를 확인해주세요.");
    }

    Member member = this.memberRepository.save(signUpRequestDto.toEntity(this.passwordEncoder));
    return member;
  }

  @Override
  public Member getMemberById(UUID id) {
    return this.memberRepository.findById(id)
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND, "회원을 찾을 수 없습니다. 아이디: " + id));
  }
}
