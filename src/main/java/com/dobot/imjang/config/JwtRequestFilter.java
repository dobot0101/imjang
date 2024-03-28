package com.dobot.imjang.config;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
  private static final Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);
  private final JwtUtil jwtUtil;

  @Qualifier("myUserDetailsService")
  private final UserDetailsService userDetailsService;

  public JwtRequestFilter(JwtUtil jwtUtil, MyUserDetailsService userDetailsService) {
    this.jwtUtil = jwtUtil;
    this.userDetailsService = userDetailsService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    final String authorizationHeader = request.getHeader("Authorization");
    String email = null;
    String jwt = null;
    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      jwt = authorizationHeader.substring(7);
      email = jwtUtil.extractEmail(jwt);
    }

    if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);

      if (jwtUtil.validateToken(jwt, userDetails)) {
        // UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new
        // UsernamePasswordAuthenticationToken(
        // userDetails, null, userDetails.getAuthorities());
        // usernamePasswordAuthenticationToken.setDetails(new
        // WebAuthenticationDetailsSource().buildDetails(request));
        Authentication authentication = new UsernamePasswordAuthenticationToken(
            userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        logger.debug("SecurityContext에  '{}' 인증 정보를 저장했습니다. uri: {}", authentication.getName(),
            request.getRequestURI());
      } else {
        logger.debug("Token is not valid");
      }
    }
    filterChain.doFilter(request, response);
  }

}
