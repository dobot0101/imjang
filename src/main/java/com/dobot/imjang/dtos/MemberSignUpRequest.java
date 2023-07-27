package com.dobot.imjang.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

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