package com.dobot.imjang.domain.member.services;

import com.dobot.imjang.domain.member.dtos.MemberSignUpRequest;
import com.dobot.imjang.domain.member.entities.Member;

public interface MemberService {
  public Member createMember(MemberSignUpRequest memberSignUpRequest);
}
