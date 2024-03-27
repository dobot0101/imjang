package com.dobot.imjang.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

// TODO: Jwt 인증을 추가하면서 JwtSpringSecurityConfig 클래스가 추가되어 이 클래스는 사용되지 않아 주석 처리함
// @Configuration
// @EnableWebSecurity
// @RequiredArgsConstructor
public class SessionSpringSecurityConfig {
        private final String[] allowedUrls = { "/auth/login", "/members/signup", "/api/members/signup", "/css/**",
                        "/js/**",
                        "/h2-console/**" };
        @Autowired
        private ObjectMapper objectMapper;

        // TODO: 현재 사용되지 않아서 주석 처리 (JWT 인증 시 사용 예정)
        // private final JwtAuthenticationFilter jwtAuthenticationFilter;
        // private final AuthenticationEntryPoint unauthorizedEntryPoint = (request,
        // response, authException) -> {
        // ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED,
        // "Spring Security Unauthorized...");
        // String json = objectMapper.writeValueAsString(errorResponse);
        // response.setStatus(HttpStatus.UNAUTHORIZED.value());
        // response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        // PrintWriter writer = response.getWriter();
        //
        // writer.write(json);
        // writer.flush();
        // };
        // private final AccessDeniedHandler accessDeniedHandler = (request, response,
        // accessDeniedException) -> {
        // ErrorResponse errorResponse = new ErrorResponse(HttpStatus.FORBIDDEN, "Spring
        // Security Forbidden...");
        // String json = objectMapper.writeValueAsString(errorResponse);
        // response.setStatus(HttpStatus.FORBIDDEN.value());
        // response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        // PrintWriter writer = response.getWriter();
        //
        // writer.write(json);
        // writer.flush();
        // };

        // @Bean
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
                // .loginProcessingUrl("/auth/login")
                // .exceptionHandling(customizer -> {
                // customizer
                // .accessDeniedHandler(this.accessDeniedHandler)
                // .authenticationEntryPoint(this.unauthorizedEntryPoint);
                // });
                // .addFilterBefore(jwtAuthenticationFilter,
                // UsernamePasswordAuthenticationFilter.class);
                return httpSecurity.build();
        }

        // @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
                        throws Exception {
                return authenticationConfiguration.getAuthenticationManager();
        }

        // @Bean
        public BCryptPasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        public record ErrorResponse(HttpStatus status, String message) {
        }
}
