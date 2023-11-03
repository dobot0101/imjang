package com.dobot.imjang.domain.auth.services;

import com.dobot.imjang.domain.member.entity.Member;

public interface AuthService {
  Member getMemberFromAuthenticatedInfo();
}