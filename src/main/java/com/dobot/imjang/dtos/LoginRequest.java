package com.dobot.imjang.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginRequest {
  @Email
  private String email;

  @NotBlank
  private String password;

  @NotBlank
  private String name;

  // @Builder
  // public MemberLoginRequest(String email, String password) {
  // this.email = email;
  // this.password = password;
  // }

  // public Member toEntity(PasswordEncoder passwordEncoder) {
  // return
  // Member.builder().id(UUID.randomUUID()).email(this.email).password(passwordEncoder.encode(this.password))
  // .build();
  // }
}