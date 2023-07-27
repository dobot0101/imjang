package com.dobot.imjang.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import lombok.Getter;

public class MemberLoginRequest {
  @Getter
  @Email
  @NotBlank
  String email;

  @NotBlank
  String password;
}