package com.dobot.imjang.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class JwtSpringSecurityConfig {
  private JwtRequestFilter jwtRequestFilter;
  private MyUserDetailsService myUserDetailsService;

  public JwtSpringSecurityConfig(JwtRequestFilter jwtRequestFilter, MyUserDetailsService myUserDetailsService) {
    this.jwtRequestFilter = jwtRequestFilter;
    this.myUserDetailsService = myUserDetailsService;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    var matcher = new AntPathRequestMatcher("/auth");
    http.csrf(c -> c.disable())
        .authorizeHttpRequests(c -> c.requestMatchers(matcher).permitAll().anyRequest().authenticated())
        .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration)
      throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(this.myUserDetailsService);
  }

}
