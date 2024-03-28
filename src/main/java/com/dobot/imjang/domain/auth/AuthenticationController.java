package com.dobot.imjang.domain.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dobot.imjang.config.JwtUtil;

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
  public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequestDto authenticationRequestDto) {
    Authentication authentication = this.authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(authenticationRequestDto.email(), authenticationRequestDto.password()));
    final String jwt = jwtUtil.generateToken(authentication);
    return ResponseEntity.ok(new AuthenticationResponseDto(jwt));
  }
}
