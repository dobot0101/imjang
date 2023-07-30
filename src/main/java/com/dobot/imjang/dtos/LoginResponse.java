package com.dobot.imjang.dtos;

import lombok.Getter;

@Getter
public class LoginResponse {
  String token;

  public LoginResponse(String token) {
    this.token = token;
  }
}
