package com.dobot.imjang.domain.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequestDto {

  @Email
  private String email;

  @NotBlank
  private String password;
}