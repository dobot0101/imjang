package com.dobot.imjang.domain.member;

import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dobot.imjang.domain.common.exception.ValidationError;
import com.dobot.imjang.domain.common.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;

  public Member signUp(SignUpRequestDto signUpRequestDto) throws Exception {
    if (memberRepository.findByEmail(signUpRequestDto.getEmail()).isPresent()) {
      throw new ValidationError("이미 존재하는 이메일입니다.");
    }

    if (!signUpRequestDto.getPassword().equals(signUpRequestDto.getConfirmPassword())) {
      throw new ValidationError("비밀번호를 확인해주세요.");
    }

    Member member = signUpRequestDto.toEntity(this.passwordEncoder);
    return this.memberRepository.save(member);
  }

  public Member getMemberById(UUID id) {
    return this.memberRepository.findById(id)
        .orElseThrow(
            () -> new ValidationError(ErrorCode.USER_NOT_FOUND, "회원을 찾을 수 없습니다. 아이디: " + id));
  }

  public Member getMemberByEmail(String email) {
    return this.memberRepository.findByEmail(email)
        .orElseThrow(
            () -> new ValidationError(ErrorCode.USER_NOT_FOUND, "회원을 찾을 수 없습니다. 이메일: " + email));
  }
}
