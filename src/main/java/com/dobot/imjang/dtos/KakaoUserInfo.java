package com.dobot.imjang.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoUserInfo {
  @NotBlank
  private String id;
  private String nickname;
  @Email
  private String email;
  private String profileImage;
}