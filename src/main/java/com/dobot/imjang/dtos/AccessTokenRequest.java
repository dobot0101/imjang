package com.dobot.imjang.dtos;

import lombok.Setter;

@Setter
public class AccessTokenRequest {
  String grantType;
  String kakaoClientId;
  String kakaoRedirectUri;
  String code;
}
