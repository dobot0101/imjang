package com.dobot.imjang.domain.auth.services;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dobot.imjang.domain.auth.dtos.LoginRequest;
import com.dobot.imjang.domain.member.entities.Member;
import com.dobot.imjang.domain.member.repositories.MemberRepository;
import com.dobot.imjang.exception.ExceptionMessage;
import com.dobot.imjang.exception.InvalidPasswordException;
import com.dobot.imjang.exception.NotFoundException;
import com.dobot.imjang.util.JwtUtil;

@Service
public class AuthService {
  MemberRepository memberRepository;
  PasswordEncoder passwordEncoder;
  JwtUtil jwtUtil;

  public AuthService(MemberRepository memberRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
    this.memberRepository = memberRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtUtil = jwtUtil;
  }

  public String login(LoginRequest loginRequest) throws Exception {
    Optional<Member> optional = this.memberRepository.findByEmail(loginRequest.getEmail());
    if (optional.isPresent()) {
      Member member = optional.get();
      if (this.passwordEncoder.matches(loginRequest.getPassword(), member.getPassword())) {
        return jwtUtil.createToken(member.getName());
      } else {
        throw new InvalidPasswordException(ExceptionMessage.INVALID_PASSWORD.getMessage());
      }
    } else {
      throw new NotFoundException(ExceptionMessage.MEMBER_NOT_FOUND.getMessage());
    }
  }
}