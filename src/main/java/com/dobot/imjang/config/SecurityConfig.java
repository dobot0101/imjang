package com.dobot.imjang.config;

import java.io.PrintWriter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  private final String[] allowedUrls = { "/auth/login", "/member/signup", "/" };
  private final JwtAuthenticationFilter jwtAuthenticationFilter;

  public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
    this.jwtAuthenticationFilter = jwtAuthenticationFilter;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        .csrf(csrfCustomizer -> csrfCustomizer.disable())
        .cors(corsCustomizer -> corsCustomizer.disable())

        /**
         * session을 사용하지 않고 매 요청마다 인증을 확인해야되는 JWT 방식을 사용하기 때문에 아래와 같이
         * SessionCreationPolicy.STATELESS 설정
         */
        .sessionManagement(sessionManagementCustomizer -> sessionManagementCustomizer
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests((authorizeHttpRequestsCustomizer -> authorizeHttpRequestsCustomizer
            .requestMatchers(this.allowedUrls).permitAll()
            .anyRequest().authenticated()))
        .exceptionHandling(exceptionHandlingConfigurer -> {
          exceptionHandlingConfigurer
              .accessDeniedHandler(this.accessDeniedHandler).authenticationEntryPoint(this.unauthorizedEntryPoint);
        })

        .addFilterBefore(jwtAuthenticationFilter, BasicAuthenticationFilter.class);
    return httpSecurity.build();
  }

  private final AuthenticationEntryPoint unauthorizedEntryPoint = (request, response, authException) -> {
    ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED,
        "Spring Security Unauthorized...");
    String json = new ObjectMapper().writeValueAsString(errorResponse);
    response.setStatus(HttpStatus.UNAUTHORIZED.value());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    PrintWriter writer = response.getWriter();

    writer.write(json);
    writer.flush();
  };

  private final AccessDeniedHandler accessDeniedHandler = (request, response, accessDeniedException) -> {
    ErrorResponse errorResponse = new ErrorResponse(HttpStatus.FORBIDDEN, "Spring Security Forbidden...");
    String json = new ObjectMapper()
        .writeValueAsString(errorResponse);
    response.setStatus(HttpStatus.FORBIDDEN.value());
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
