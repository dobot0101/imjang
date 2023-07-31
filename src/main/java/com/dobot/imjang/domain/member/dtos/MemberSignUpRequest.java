package com.dobot.imjang.domain.member.dtos;

import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.dobot.imjang.domain.member.entities.Member;

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

  @NotBlank
  private String name;

  @Builder
  public MemberSignUpRequest(String email, String password, String name) {
    this.email = email;
    this.name = name;
    this.password = password;
  }

  public Member toEntity(PasswordEncoder passwordEncoder) {
    return Member.builder().id(UUID.randomUUID()).email(this.email).password(passwordEncoder.encode(this.password))
        .name(name)
        .build();
  }
}