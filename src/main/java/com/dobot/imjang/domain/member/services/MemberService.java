package com.dobot.imjang.domain.member.services;

import java.util.UUID;

import com.dobot.imjang.domain.member.dtos.MemberSignUpRequest;
import com.dobot.imjang.domain.member.entities.Member;

public interface MemberService {
  public Member getMemberById(UUID id);

  public Member createMember(MemberSignUpRequest memberSignUpRequest);
}
