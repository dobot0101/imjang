package com.dobot.imjang.config;

import java.io.PrintWriter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity.csrf(csrfCustomizer -> csrfCustomizer.disable())
        .authorizeHttpRequests((authorizeHttpRequestsCustomizer -> authorizeHttpRequestsCustomizer
            .requestMatchers("/member/signup").permitAll().anyRequest().authenticated()))
        .exceptionHandling(exceptionHandlingConfigurer -> {
          exceptionHandlingConfigurer.authenticationEntryPoint(this.unauthorizedEntryPoint)
              .accessDeniedHandler(this.accessDeniedHandler);
        });
    return httpSecurity.build();
  }

  private final AuthenticationEntryPoint unauthorizedEntryPoint = (request, response, authException) -> {
    ErrorResponse fail = new ErrorResponse(HttpStatus.UNAUTHORIZED, "Spring security unauthorized...");
    response.setStatus(HttpStatus.UNAUTHORIZED.value());
    String json = new ObjectMapper().writeValueAsString(fail);
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    PrintWriter writer = response.getWriter();
    writer.write(json);
    writer.flush();
  };

  private final AccessDeniedHandler accessDeniedHandler = (request, response, accessDeniedException) -> {
    ErrorResponse fail = new ErrorResponse(HttpStatus.FORBIDDEN, "Spring security forbidden...");
    response.setStatus(HttpStatus.FORBIDDEN.value());
    String json = new ObjectMapper().writeValueAsString(fail);
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    PrintWriter writer = response.getWriter();
    writer.write(json);
    writer.flush();
  };

  @Getter
  @RequiredArgsConstructor
  public class ErrorResponse {

    private final HttpStatus status;
    private final String message;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
