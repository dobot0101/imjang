package com.dobot.imjang.domain.member.dto;

import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.dobot.imjang.domain.member.entity.Member;
import com.dobot.imjang.domain.member.entity.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class SignUpRequestDto {
  @Email
  @NotBlank(message = "이메일을 입력하세요")
  private String email;

  @NotBlank(message = "비밀번호를 임력하세요")
  @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,16}$", message = "비밀번호는 8~16 자리이면서 1개 이상의 알파벳, 숫자, 특수문자를 포함해야합니다.")
  private String password;

  @NotBlank
  private String confirmPassword;

  @NotBlank(message = "이름을 입력하세요")
  @Size(min = 2, message = "이름을 2자 이상 입력하세요")
  private String name;

  // @Builder 어노테이션은 초기화를 무시하기 때문에 초기화하려면 필드에 final을 붙여야 함
  private final Role role = Role.USER;

  public Member toEntity(PasswordEncoder passwordEncoder) {
    return Member.builder().id(UUID.randomUUID()).email(this.email).password(passwordEncoder.encode(this.password))
        .name(name).role(role)
        .build();
  }
}