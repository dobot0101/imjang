package com.dobot.imjang.dtos;

import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.dobot.imjang.entities.Member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberSignUpRequest {
  @Email
  private String email;

  @NotBlank
  private String password;

  @Builder
  public MemberSignUpRequest(String email, String password) {
    this.email = email;
    this.password = password;
  }

  public Member toEntity(PasswordEncoder passwordEncoder) {
    return Member.builder().id(UUID.randomUUID()).email(this.email).password(passwordEncoder.encode(this.password))
        .build();
  }
}