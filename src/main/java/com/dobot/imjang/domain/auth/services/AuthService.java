package com.dobot.imjang.domain.auth.services;

import com.dobot.imjang.domain.auth.dtos.LoginResponseDto;

public interface AuthService {
  LoginResponseDto login(String username, String password);
}