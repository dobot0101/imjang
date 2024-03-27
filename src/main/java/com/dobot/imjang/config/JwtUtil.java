package com.dobot.imjang.config;

import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {
  private String SECRET_KEY = "secret";

  public String generateToken(Authentication authentication) {
    UserDetails principal = (UserDetails) authentication.getPrincipal();
    return Jwts.builder().setSubject(principal.getUsername()).setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
        .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
  }

  public boolean validateToken(String token, UserDetails userDetails) {
    final String username = extractMemberEmail(token);
    return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
  }

  private boolean isTokenExpired(String token) {
    var parser = Jwts.parserBuilder().setSigningKey(SECRET_KEY).build();
    return parser.parseClaimsJws(token).getBody().getExpiration().before(new Date());
  }

  public String extractMemberEmail(String token) {
    var parser = Jwts.parserBuilder().setSigningKey(SECRET_KEY).build();
    return parser.parseClaimsJws(token).getBody().getSubject();
  }

}
