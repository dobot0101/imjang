package com.dobot.imjang.domain.member;

import java.util.UUID;

public interface MemberService {
  public Member getMemberById(UUID id);

  public Member getMemberByEmail(String email);

  public Member signUp(SignUpRequestDto memberSignUpRequest) throws Exception;
}
