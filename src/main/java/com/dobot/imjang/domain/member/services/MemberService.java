package com.dobot.imjang.domain.member.services;

import java.util.UUID;

import com.dobot.imjang.domain.member.dtos.SignUpRequestDto;
import com.dobot.imjang.domain.member.entities.Member;

public interface MemberService {
  public Member getMemberById(UUID id);

  public Member signUp(SignUpRequestDto memberSignUpRequest) throws Exception;
}
