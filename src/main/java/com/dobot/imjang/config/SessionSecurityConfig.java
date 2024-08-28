package com.dobot.imjang.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.Arrays;

//지금은 JwtSecurityConfig을 사용하고 있기 때문에 아래의 애노테이션을 주석 처리함
//@Configuration
//@EnableWebSecurity
@RequiredArgsConstructor
public class SessionSecurityConfig {
    private final String[] allowedUrls = {"/auth/login", "/members/signup", "/api/members/signup", "/css/**",
            "/js/**",
            "/h2-console/**"};

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        RequestMatcher[] requestMatchers = Arrays.stream(allowedUrls).map(AntPathRequestMatcher::new)
                .toArray(RequestMatcher[]::new);
        httpSecurity.csrf(AbstractHttpConfigurer::disable).cors(AbstractHttpConfigurer::disable)
                .formLogin(customizer -> customizer.loginPage("/auth/login").defaultSuccessUrl("/")
                        .failureUrl("/auth/login").usernameParameter("email")
                        .passwordParameter("password").permitAll())
                .sessionManagement(customizer -> customizer.invalidSessionUrl("/auth/login"))
                .logout(customizer -> customizer.logoutUrl("/auth/logout")
                        .logoutSuccessUrl("/auth/login")
                        .invalidateHttpSession(true).deleteCookies("JSESSIONID"))
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        (customizer -> customizer.requestMatchers(requestMatchers).permitAll()
                                .anyRequest().authenticated()))
                // 아래의 frameOptions를 disable로 설정하지 않으면 h2-console 접속이 안됨
                .headers(customizer -> customizer
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));
        return httpSecurity.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
