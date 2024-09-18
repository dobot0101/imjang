package com.dobot.imjang.config;

import com.dobot.imjang.domain.auth.CustomUserDetails;
import com.dobot.imjang.domain.common.exception.ValidationError;
import com.dobot.imjang.domain.common.exception.ErrorCode;
import com.dobot.imjang.domain.member.Member;
import com.dobot.imjang.domain.member.MemberRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

  private final MemberRepository memberRepository;

  @Override
  public UserDetails loadUserByUsername(String email) {
    Member member = this.memberRepository.findByEmail(email).orElseThrow(() ->
        new RuntimeException("유저를 찾을 수 없습니다."));
    return new CustomUserDetails(member.getId(), member.getEmail(), member.getPassword(), null);
  }
}
