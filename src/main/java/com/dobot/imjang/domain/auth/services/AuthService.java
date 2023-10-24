package com.dobot.imjang.domain.auth.services;

import com.dobot.imjang.domain.auth.dtos.LoginRequestDto;

public interface AuthService {

  public String login(LoginRequestDto loginRequestDto) throws Exception;
}