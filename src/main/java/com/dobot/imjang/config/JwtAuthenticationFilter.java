package com.dobot.imjang.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationFilter implements AuthenticationSuccessHandler {

  private final JwtUtil jwtUtil;

  public JwtAuthenticationFilter(JwtUtil jwtUtil) {
    this.jwtUtil = jwtUtil;
  }

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {
    String jwt = jwtUtil.generateToken(authentication.getName());
    response.setHeader("Authorization", "Bearer " + jwt);
    response.sendRedirect("/home");
//    response.setContentType("application/json");
//    Map<String, String> tokenMap = new HashMap<>();
//    tokenMap.put("token", jwt);
//    new ObjectMapper().writeValue(response.getWriter(), tokenMap);
  }
}