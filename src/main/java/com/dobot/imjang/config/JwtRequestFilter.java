package com.dobot.imjang.config;

import com.dobot.imjang.common.JwtCookieService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 이 필터는 모든 http 요청마다 한번씩 실행됨. 토큰을 검증하고 SecurityContext에 인증 정보를 저장함.
 */
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
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    String jwt = null;
    String email = null;

    // 쿠키 방식
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals("jwt")) {
          logger.debug("jwt cookie: {}", cookie.getValue());
          jwt = cookie.getValue();

          // 쿠키가 아직 유효하면 만료 시간을 1시간 후로 업데이트 함
          if (jwtUtil.validateToken(jwt)) {
            JwtCookieService.createJwtCookie(response, jwt);

            email = jwtUtil.extractEmailFromToken(jwt);
          } else {
            // jwt 토큰이 유효하지 않으면 쿠키를 삭제하고 401 에러를 반환함
            JwtCookieService.deleteJwtCookie(response);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT Token");
            return;
          }
        }
      }
    }

    // jwt 토큰의 이메일 값은 있지만 SecurityContext에 인증 정보가 없으면 인증 정보를 저장함
    if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

      try {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);

        if (jwtUtil.validateToken(jwt)) {
          Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
              userDetails.getAuthorities());
          SecurityContextHolder.getContext().setAuthentication(authentication);
          logger.debug("SecurityContext에  '{}' 인증 정보를 저장했습니다. uri: {}", authentication.getName(),
              request.getRequestURI());
        } else {
          logger.debug("Token is not valid");
          response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT Token");
          return;
        }
      } catch (UsernameNotFoundException e) {
        logger.error("User not found: {}", email);
        // 유효하지 않은 토큰일 경우 쿠키 삭제
        JwtCookieService.deleteJwtCookie(response);
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not found");
        return;
      }
    }
    filterChain.doFilter(request, response);
  }


}
