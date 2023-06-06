package com.dobot.imjang.dtos;

public class AccessTokenResponse {
  String accessToken;
  String tokenType;
  String idToken;
  Integer expiresIn;
  String refreshToken;
  Integer refreshTokenExpiresIn;
  String scope;

  public String getAccessToken() {
    return this.accessToken;
  }
}
