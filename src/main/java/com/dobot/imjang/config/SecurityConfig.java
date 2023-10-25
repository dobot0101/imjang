package com.dobot.imjang.config;

import java.io.PrintWriter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.dobot.imjang.domain.auth.services.CustomUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
  private final String[] allowedUrls = { "/auth/login", "/member/signup", "/" };
  private final JwtAuthenticationFilter authenticationFilter;
  private final CustomUserDetailsService userDetailsService;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        .csrf(customizer -> customizer.disable())
        .cors(customizer -> customizer.disable())
        .formLogin(customizer -> customizer.disable())
        .httpBasic(customizer -> customizer.disable())
        .sessionManagement(sessionManagementCustomizer -> sessionManagementCustomizer
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests((customizer -> customizer
            .requestMatchers(this.allowedUrls)
            .permitAll()
            .anyRequest()
            .authenticated()))
        .exceptionHandling(customizer -> {
          customizer
              .accessDeniedHandler(this.accessDeniedHandler)
              .authenticationEntryPoint(this.unauthorizedEntryPoint);
        })
        .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
    return httpSecurity.build();
  }

  @Bean
  public AuthenticationManager authenticationManager(HttpSecurity http,
      BCryptPasswordEncoder bcryptPasswordEncoder) throws Exception {
    AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
    builder.userDetailsService(userDetailsService).passwordEncoder(bcryptPasswordEncoder);
    return builder.build();
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
