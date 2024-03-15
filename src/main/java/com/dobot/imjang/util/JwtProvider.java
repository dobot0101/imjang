package com.dobot.imjang.util;

import com.dobot.imjang.domain.auth.CustomUserDetailsService;
import com.dobot.imjang.domain.common.exception.CustomException;
import com.dobot.imjang.domain.common.exception.ErrorCode;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

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

    public String generateToken(UserDetails userDetails) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + expiration);

        Claims claims = Jwts.claims().setSubject(userDetails.getUsername());
        claims.put("roles", userDetails.getAuthorities());

        String jwt = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(new SecretKeySpec(secret.getBytes(),
                        SignatureAlgorithm.HS256.getJcaName()))
                .compact();

        return jwt;
    }

    public Authentication getAuthentication(String token) {
        String username = Jwts.parserBuilder().setSigningKey(secret.getBytes()).build().parseClaimsJws(token).getBody()
                .getSubject();
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), "", userDetails.getAuthorities());
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

    public String resolveToken(HttpServletRequest request) {
        String jwt = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(jwt) && jwt.startsWith("Bearer ")) {
            return jwt.substring(7);
        }
        return null;
    }
}
