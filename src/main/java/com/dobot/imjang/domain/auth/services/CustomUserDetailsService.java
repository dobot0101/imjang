package com.dobot.imjang.domain.auth.services;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.dobot.imjang.domain.member.entities.Member;
import com.dobot.imjang.domain.member.respositories.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class CustomUserDetailsService implements UserDetailsService {
  private final MemberRepository memberRepository;

  @Override
  public UserDetails loadUserByUsername(String username) {
    Member member = this.memberRepository.findByEmail(username)
        .orElseThrow(() -> new UsernameNotFoundException("회원 정보를 찾을 수 없습니다."));

    UserDetails userDetails = User.builder().username(member.getEmail()).password(member.getPassword())
        .roles(member.getRole().name())
        .build();

    return userDetails;
  }
}