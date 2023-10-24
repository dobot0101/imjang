package com.dobot.imjang.domain.member.dtos;

import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.dobot.imjang.domain.member.entities.Member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequestDto {
  @Email
  private String email;

  @NotBlank
  private String password;

  @NotBlank
  private String confirmPassword;

  @NotBlank
  private String name;

  public Member toEntity(PasswordEncoder passwordEncoder) {
    return Member.builder().id(UUID.randomUUID()).email(this.email).password(passwordEncoder.encode(this.password))
        .name(name)
        .build();
  }
}