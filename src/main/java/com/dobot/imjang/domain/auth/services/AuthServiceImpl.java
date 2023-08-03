package com.dobot.imjang.domain.auth.services;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dobot.imjang.domain.auth.dtos.LoginRequest;
import com.dobot.imjang.domain.common.exceptions.ExceptionMessage;
import com.dobot.imjang.domain.common.exceptions.InvalidPasswordException;
import com.dobot.imjang.domain.common.exceptions.NotFoundException;
import com.dobot.imjang.domain.member.entities.Member;
import com.dobot.imjang.domain.member.repositories.MemberRepository;
import com.dobot.imjang.util.JwtProvider;

@Service
public class AuthServiceImpl implements AuthService {
  MemberRepository memberRepository;
  PasswordEncoder passwordEncoder;
  JwtProvider jwtUtil;

  public AuthServiceImpl(MemberRepository memberRepository, PasswordEncoder passwordEncoder, JwtProvider jwtUtil) {
    this.memberRepository = memberRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtUtil = jwtUtil;
  }

  public String login(LoginRequest loginRequest) throws Exception {
    Optional<Member> optional = this.memberRepository.findByEmail(loginRequest.getEmail());
    if (optional.isPresent()) {
      Member member = optional.get();
      if (this.passwordEncoder.matches(loginRequest.getPassword(), member.getPassword())) {
        return jwtUtil.createToken(member.getId().toString());
      } else {
        throw new InvalidPasswordException(ExceptionMessage.INVALID_PASSWORD.getMessage());
      }
    } else {
      throw new NotFoundException(ExceptionMessage.MEMBER_NOT_FOUND.getMessage());
    }
  }
}