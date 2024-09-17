package com.dobot.imjang.domain.auth;

import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dobot.imjang.config.JwtUtil;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;

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
  public ResponseEntity<?> createAuthenticationToken(
      AuthenticationRequestDto authenticationRequestDto) {
    Authentication authentication = this.authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(authenticationRequestDto.email(),
            authenticationRequestDto.password()));
    final String jwt = jwtUtil.generateToken(authentication.getName());
    return ResponseEntity.ok(new AuthenticationResponseDto(jwt));
  }

  @GetMapping("/verify")
  public ResponseEntity<?> verifyToken(HttpServletRequest request) {
    String authHeader = request.getHeader("Authorization");
    if (authHeader != null && authHeader.startsWith("Bearer ")) {

      try {
        String token = authHeader.split("Bearer ")[1];
        if (jwtUtil.validateToken(token)) {
          return ResponseEntity.ok("Token is valid");
        }
      } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("invalid token");
      }
    }
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization header is invalid");
  }
}
