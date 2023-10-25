package com.dobot.imjang.domain.auth.dtos;

import lombok.Getter;

@Getter
public class LoginResponseDto {
  public LoginResponseDto(String email, String token) {
    this.email = email;
    this.token = token;
  }

  private String email;
  private String token;
}
