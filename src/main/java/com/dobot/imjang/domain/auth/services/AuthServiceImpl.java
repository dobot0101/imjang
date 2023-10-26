package com.dobot.imjang.domain.auth.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.dobot.imjang.domain.auth.dtos.LoginResponseDto;
import com.dobot.imjang.util.JwtProvider;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtProvider jwtProvider;

    @Override
    public LoginResponseDto login(String email, String password) {
        /*
         * authenticationManager.authenticate()를 실행하면 파라미터로 전달한
         * UsernamePasswordAuthenticationToken의 password와 자동으로 UserDetailsService의
         * loadUserByUsername를 실행해서 만드는 Authentication의 password를 비교함. 그래서
         * AuthenticationProvider를 따로 구현할 필요없음.
         */
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        String token = jwtProvider.generateToken(userDetails);
        LoginResponseDto dto = LoginResponseDto.builder().email(userDetails.getUsername()).token(token).build();

        return dto;
    }
}
