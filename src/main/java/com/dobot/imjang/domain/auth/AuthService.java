package com.dobot.imjang.domain.auth;

import com.dobot.imjang.domain.member.Member;

public interface AuthService {
  Member getMemberFromAuthenticatedInfo();
}