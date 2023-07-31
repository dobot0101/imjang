package com.dobot.imjang.domain.auth.dtos;

import lombok.Getter;

@Getter
public class LoginResponse {
  String token;

  public LoginResponse(String token) {
    this.token = token;
  }
}
