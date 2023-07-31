package com.dobot.imjang.domain.auth.dtos;

import lombok.Getter;

@Getter
public class JwtResponse {
  private String token;

  public JwtResponse(String token) {
    this.token = token;
  }
}