package com.dobot.imjang.domain.auth.dtos;

import lombok.Getter;

@Getter
public class LoginResponseDto {
  public LoginResponseDto(String email, String name, String token) {
    this.email = email;
    this.name = name;
    this.token = token;
  }

  private String email;
  private String name;
  private String token;
}
