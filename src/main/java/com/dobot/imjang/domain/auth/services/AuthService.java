package com.dobot.imjang.domain.auth.services;

import com.dobot.imjang.domain.auth.dtos.LoginRequest;

public interface AuthService {

  public String login(LoginRequest loginRequest) throws Exception;
}