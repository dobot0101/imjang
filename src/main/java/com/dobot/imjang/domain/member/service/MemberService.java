package com.dobot.imjang.domain.member.service;

import java.util.UUID;

import com.dobot.imjang.domain.member.dto.SignUpRequestDto;
import com.dobot.imjang.domain.member.entity.Member;

public interface MemberService {
  public Member getMemberById(UUID id);

  public Member getMemberByEmail(String email);

  public Member signUp(SignUpRequestDto memberSignUpRequest) throws Exception;
}
