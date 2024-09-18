package com.dobot.imjang.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class JwtSecurityConfig {

  private final JwtRequestFilter jwtRequestFilter;
  private final MyUserDetailsService myUserDetailsService;
  // login form 사용 시 사용
//  private final JwtAuthenticationFilter jwtAuthenticationFilter;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    AntPathRequestMatcher[] matchers = {new AntPathRequestMatcher("/api/auth"),
        new AntPathRequestMatcher("/login"), new AntPathRequestMatcher("/signup"),
        new AntPathRequestMatcher("/css/**"), new AntPathRequestMatcher("/js/**"),
        new AntPathRequestMatcher("/img/**"), new AntPathRequestMatcher("/favicon.ico")};
    http.csrf(AbstractHttpConfigurer::disable).formLogin(AbstractHttpConfigurer::disable)
        .httpBasic(AbstractHttpConfigurer::disable).authorizeHttpRequests(
            c -> c.requestMatchers(matchers).permitAll().anyRequest().authenticated()).formLogin(c -> {
          c.loginPage("/login");
        })
        .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        
//        .formLogin(c -> {
//          c.loginProcessingUrl("/api/auth");
//          c.loginPage("/login");
//          c.successHandler(jwtAuthenticationFilter);
//        }).logout(c -> {
//          c.logoutSuccessUrl("/login").invalidateHttpSession(true);
//        });
    http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
//    return new BCryptPasswordEncoder();
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManagerBean(
      AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Autowired
  public void configure(AuthenticationManagerBuilder authenticationManagerBuilder)
      throws Exception {
    authenticationManagerBuilder.userDetailsService(this.myUserDetailsService);
  }

}
