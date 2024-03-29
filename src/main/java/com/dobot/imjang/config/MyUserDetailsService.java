package com.dobot.imjang.config;

import java.util.UUID;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dobot.imjang.domain.common.exception.CustomException;
import com.dobot.imjang.domain.common.exception.ErrorCode;
import com.dobot.imjang.domain.member.Member;
import com.dobot.imjang.domain.member.MemberRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {
  private final MemberRepository memberRepository;

  public MyUserDetailsService(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
    Member member = this.memberRepository.findById(UUID.fromString(memberId))
        .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    return User.withUsername(member.getId().toString()).password(member.getPassword())
        .authorities(member.getRole().name())
        .build();
  }

}
