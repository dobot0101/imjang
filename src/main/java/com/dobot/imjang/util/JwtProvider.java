package com.dobot.imjang.util;

import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtProvider {
  private final String secret;
  private final long expiration;

  public JwtProvider(@Value("${jwt.secret}") String secret, @Value("${jwt.expiration}") long expiration) {
    this.secret = secret;
    this.expiration = expiration;
  }

  public String createToken(String memberId) {
    Date now = new Date();
    Date expirationDate = new Date(now.getTime() + expiration);

    return Jwts.builder()
        .setSubject(memberId)
        .setIssuedAt(now)
        .setExpiration(expirationDate)
        .signWith(new SecretKeySpec(secret.getBytes(),
            SignatureAlgorithm.HS256.getJcaName()))
        .compact();

  }

  public String validateTokenAndGetSubject(String token) {
    return Jwts.parserBuilder().setSigningKey(secret.getBytes()).build().parseClaimsJws(token).getBody().getSubject();
  }
}
