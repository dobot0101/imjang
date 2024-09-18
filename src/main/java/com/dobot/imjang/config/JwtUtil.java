package com.dobot.imjang.config;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.dobot.imjang.domain.common.exception.ValidationError;
import com.dobot.imjang.domain.common.exception.ErrorCode;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtUtil {

  private final String secretKey;
  private final long validityInMilliseconds;

  public JwtUtil(@Value("${jwt.secret}") String secretKey,
      @Value("${jwt.expiration}") long validityInMilliseconds) {
    this.secretKey = secretKey;
    this.validityInMilliseconds = validityInMilliseconds;
  }

  public String generateToken(String name) {
    Date now = new Date();
    Date validity = new Date(now.getTime() + this.validityInMilliseconds);

    return Jwts.builder().setSubject(name).setIssuedAt(new Date())
        .setExpiration(validity)
        // .signWith(new SecretKeySpec(this.secretKey.getBytes(),
        // SignatureAlgorithm.HS256.getJcaName()))
        // .compact();
        // deprecated
        .signWith(SignatureAlgorithm.HS256, this.secretKey).compact();
  }

  public boolean validateToken(String token) {
    try {
      return !isTokenExpired(token);
    } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
      log.info("잘못된 JWT 서명입니다.");
      throw new ValidationError(ErrorCode.JWT_NOT_VALID, "잘못된 JWT 서명입니다.");
    } catch (ExpiredJwtException e) {
      log.info("만료된 JWT 토큰입니다.");
      throw new ValidationError(ErrorCode.JWT_NOT_VALID, "만료된 JWT 토큰입니다.");
    } catch (UnsupportedJwtException e) {
      log.info("지원되지 않는 JWT 토큰입니다.");
      throw new ValidationError(ErrorCode.JWT_NOT_VALID, "지원되지 않는 JWT 토큰입니다.");
    } catch (IllegalArgumentException e) {
      log.info("JWT 토큰이 잘못되었습니다.");
      throw new ValidationError(ErrorCode.JWT_NOT_VALID, "JWT 토큰이 잘못되었습니다.");
    }
  }

  private boolean isTokenExpired(String token) {
    var parser = Jwts.parserBuilder().setSigningKey(this.secretKey).build();
    return parser.parseClaimsJws(token).getBody().getExpiration().before(new Date());
  }

  public String extractEmailFromToken(String token) {
    var parser = Jwts.parserBuilder().setSigningKey(this.secretKey).build();
    return parser.parseClaimsJws(token).getBody().getSubject();
  }

}
