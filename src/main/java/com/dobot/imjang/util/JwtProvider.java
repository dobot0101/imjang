package com.dobot.imjang.util;

import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.dobot.imjang.domain.auth.services.CustomUserDetailsService;
import com.dobot.imjang.domain.common.exception.CustomException;
import com.dobot.imjang.domain.common.exception.ErrorCode;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtProvider {
  private final String secret;
  private final long expiration;
  private final CustomUserDetailsService userDetailsService;

  public JwtProvider(@Value("${jwt.secret}") String secret, @Value("${jwt.expiration}") long expiration,
      CustomUserDetailsService userDetailsService) {
    this.secret = secret;
    this.expiration = expiration;
    this.userDetailsService = userDetailsService;
  }

  public String generateToken(UserDetails user) {
    Date now = new Date();
    Date expirationDate = new Date(now.getTime() + expiration);
    Claims claims = Jwts.claims();
    claims.put("roles", user.getAuthorities());

    return Jwts.builder()
        .setSubject(user.getUsername())
        .setClaims(claims)
        .setIssuedAt(now)
        .setExpiration(expirationDate)
        .signWith(new SecretKeySpec(secret.getBytes(),
            SignatureAlgorithm.HS256.getJcaName()))
        .compact();
  }

  public Authentication getAuthentication(String token) {
    UserDetails userDetails = userDetailsService.loadUserByUsername(this.extractMemberId(token));
    return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), "", userDetails.getAuthorities());
  }

  public String extractMemberId(String token) {
    return Jwts.parserBuilder().setSigningKey(secret.getBytes()).build().parseClaimsJwt(token).getBody().getSubject();
  }

  public boolean isTokenExpired(String token) {
    Date expiration = Jwts.parserBuilder().setSigningKey(secret.getBytes()).build().parseClaimsJws(token).getBody()
        .getExpiration();
    return expiration.before(new Date());
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(secret.getBytes()).build().parseClaimsJws(token);
      return true;
    } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
      log.info("잘못된 JWT 서명입니다.");
      throw new CustomException(ErrorCode.JWT_NOT_VALID, "잘못된 JWT 서명입니다.");
    } catch (ExpiredJwtException e) {
      log.info("만료된 JWT 토큰입니다.");
      throw new CustomException(ErrorCode.JWT_NOT_VALID, "만료된 JWT 토큰입니다.");
    } catch (UnsupportedJwtException e) {
      log.info("지원되지 않는 JWT 토큰입니다.");
      throw new CustomException(ErrorCode.JWT_NOT_VALID, "지원되지 않는 JWT 토큰입니다.");
    } catch (IllegalArgumentException e) {
      log.info("JWT 토큰이 잘못되었습니다.");
      throw new CustomException(ErrorCode.JWT_NOT_VALID, "JWT 토큰이 잘못되었습니다.");
    }
  }
}
