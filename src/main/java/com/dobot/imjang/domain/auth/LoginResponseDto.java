package com.dobot.imjang.domain.auth;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginResponseDto {
  @Builder
  public LoginResponseDto(String email, String token, String errorMessage) {
    this.email = email;
    this.token = token;
    this.errorMessage = errorMessage;
  }

  private String email;
  private String token;
  private String errorMessage;
}
