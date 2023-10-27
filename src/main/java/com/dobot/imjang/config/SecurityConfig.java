package com.dobot.imjang.config;

import java.io.PrintWriter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
// @RequiredArgsConstructor
public class SecurityConfig {
  private final String[] allowedUrls = { "/auth/login", "/member/signup" };
  // private final JwtAuthenticationFilter jwtAuthenticationFilter;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        .csrf(customizer -> customizer.disable())
        .cors(customizer -> customizer.disable())
        .formLogin(
            customizer -> customizer
                .loginPage("/auth/login")
                .defaultSuccessUrl("/")
                .loginProcessingUrl("/auth/login")
                .failureUrl("/auth/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .permitAll())
        .logout(
            customizer -> customizer
                .logoutUrl("/auth/logout")
                .logoutSuccessUrl("/auth/login")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID"))
        .httpBasic(customizer -> customizer.disable())
        .authorizeHttpRequests((customizer -> customizer
            .requestMatchers(this.allowedUrls)
            .permitAll()
            .anyRequest()
            .authenticated()));
    // .exceptionHandling(customizer -> {
    // customizer
    // .accessDeniedHandler(this.accessDeniedHandler)
    // .authenticationEntryPoint(this.unauthorizedEntryPoint);
    // });
    // .addFilterBefore(jwtAuthenticationFilter,
    // UsernamePasswordAuthenticationFilter.class);
    return httpSecurity.build();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
      throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
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
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
