package com.dobot.imjang.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberSignUpRequest {
  @Email
  private String email;

  @NotBlank
  private String password;
}