package com.dobot.imjang.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dobot.imjang.dtos.AuthLoginRequest;
import com.dobot.imjang.entities.Member;
import com.dobot.imjang.repository.MemberRepository;

@Service
public class AuthService {
  MemberRepository memberRepository;
  PasswordEncoder passwordEncoder;

  public AuthService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
    this.memberRepository = memberRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public String login(AuthLoginRequest authLoginRequest) {
    Optional<Member> optional = this.memberRepository.findByEmail(authLoginRequest.getEmail());
    if (optional.isPresent()) {
      Member member = optional.get();
      if (this.passwordEncoder.matches(authLoginRequest.getPassword(), member.getPassword())) {
        // JWT 생성 및 반환
      }
    }
    return null;
  }
}
