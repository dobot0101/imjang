package com.dobot.imjang.dtos;

import lombok.Getter;

@Getter
public class AccessTokenResponse {
  String accessToken;
  String tokenType;
  String idToken;
  Integer expiresIn;
  String refreshToken;
  Integer refreshTokenExpiresIn;
  String scope;
}
