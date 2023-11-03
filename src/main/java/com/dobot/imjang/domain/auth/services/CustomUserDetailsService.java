package com.dobot.imjang.domain.auth.services;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.dobot.imjang.domain.auth.entity.CustomUserDetails;
import com.dobot.imjang.domain.member.entity.Member;
import com.dobot.imjang.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class CustomUserDetailsService implements UserDetailsService {
  private final MemberRepository memberRepository;

  @Override
  public UserDetails loadUserByUsername(String username) {
    Member member = this.memberRepository.findByEmail(username)
        .orElseThrow(() -> new UsernameNotFoundException("회원 정보를 찾을 수 없습니다."));

    // UserDetails userDetails =
    // User.builder().username(member.getEmail()).password(member.getPassword())
    // .roles(member.getRole().name())
    // .build();

    UserDetails userDetails = new CustomUserDetails(member.getId(), member.getName(), member.getPassword(),
        member.getEmail(),
        Collections.singleton(new SimpleGrantedAuthority(member.getRole().name())));

    return userDetails;
  }

  
}
