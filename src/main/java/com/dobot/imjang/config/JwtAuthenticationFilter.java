package com.dobot.imjang.config;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import com.dobot.imjang.domain.member.entities.Member;
import com.dobot.imjang.domain.member.repositories.MemberRepository;
import com.dobot.imjang.domain.member.services.MemberService;
import com.dobot.imjang.util.JwtProvider;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
  private final JwtProvider jwtProvider;
  private final MemberService memberService;

  public JwtAuthenticationFilter(JwtProvider jwtProvider, MemberService memberService) {
    this.jwtProvider = jwtProvider;
    this.memberService = memberService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String token = parseBearerToken(request);
    User user = parseUserSpecification(token);
    AbstractAuthenticationToken authenticated = UsernamePasswordAuthenticationToken.authenticated(user, token,
        user.getAuthorities());
    authenticated.setDetails(new WebAuthenticationDetails(request));
    SecurityContextHolder.getContext().setAuthentication(authenticated);

    filterChain.doFilter(request, response);
  }

  private String parseBearerToken(HttpServletRequest request) {
    return Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION))
        .filter(token -> token.substring(0, 7).equalsIgnoreCase("Bearer "))
        .map(token -> token.substring(7))
        .orElse(null);
  }

  private User parseUserSpecification(String token) {
    String[] split = Optional.ofNullable(token)
        .filter(subject -> subject.length() >= 10)
        .map(jwtProvider::getUsernameFromToken)
        .orElse("anonymous:anonymous")
        .split(":");

    return new User(split[0], "", List.of(new SimpleGrantedAuthority(split[1])));
  }
}
