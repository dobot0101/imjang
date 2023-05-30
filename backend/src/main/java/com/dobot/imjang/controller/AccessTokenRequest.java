package com.dobot.imjang.controller;

public class AccessTokenRequest {
  String grantType;
  String kakaoClientId;
  String kakaoRedirectUri;
  String code;

  public void setGrantType(String grantType) {
    this.grantType = grantType;
  }

  public void setClientId(String kakaoClientId) {
    this.kakaoClientId = kakaoClientId;
  }

  public void setRedirectUri(String kakaoRedirectUri) {
    this.kakaoRedirectUri = kakaoRedirectUri;
  }

  public void setCode(String code) {
    this.code = code;
  }

}
