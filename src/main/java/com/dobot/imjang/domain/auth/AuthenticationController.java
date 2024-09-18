package com.dobot.imjang.domain.auth;

import com.dobot.imjang.common.JwtCookieService;
import com.dobot.imjang.config.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

  private final AuthenticationManager authenticationManager;
  private final JwtUtil jwtUtil;

  public AuthenticationController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
    this.authenticationManager = authenticationManager;
    this.jwtUtil = jwtUtil;
  }

  @PostMapping
  public ResponseEntity<?> login(
      LoginRequestDto loginRequestDto, HttpServletResponse response) {

    // 인증
    Authentication authentication = this.authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(),
            loginRequestDto.getPassword()));

    // jwt 토큰 생성 및 쿠키에 저장
    final String jwt = jwtUtil.generateToken(authentication.getName());
    JwtCookieService.createJwtCookie(response, jwt);

    return ResponseEntity.ok(new AuthenticationResponseDto(jwt));
  }

  @PostMapping("/logout")
  public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {

    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals("jwt")) {
          final String jwt = cookie.getValue();

          try {
            if (jwtUtil.validateToken(jwt)) {
              JwtCookieService.deleteJwtCookie(response);
              return ResponseEntity.ok("Logout success");
            }
          } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("invalid token");
          }
        }
      }
    }
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("token not found");
  }
}
