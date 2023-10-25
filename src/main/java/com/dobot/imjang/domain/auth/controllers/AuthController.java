package com.dobot.imjang.domain.auth.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dobot.imjang.domain.auth.dtos.LoginRequestDto;
import com.dobot.imjang.domain.auth.dtos.LoginResponseDto;
import com.dobot.imjang.domain.auth.services.CustomUserDetailsService;
import com.dobot.imjang.util.JwtProvider;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
  private final JwtProvider jwtProvider;
  private final AuthenticationManager authenticationManager;
  private final CustomUserDetailsService userDetailsService;

  @PostMapping("/login")
  public ResponseEntity<?> login(@Valid LoginRequestDto loginRequestDto,
      BindingResult bindingResult)
      throws Exception {
    if (bindingResult.hasErrors()) {
      StringBuilder errorMessage = new StringBuilder();
      for (FieldError error : bindingResult.getFieldErrors()) {
        errorMessage.append(error.getDefaultMessage()).append("; ");
      }
      return ResponseEntity.badRequest().body(errorMessage.toString());
    }

    /*
     * authenticationManager.authenticate()를 실행하면 파라미터로 전달한
     * UsernamePasswordAuthenticationToken의 password와 자동으로 UserDetailsService의
     * loadUserByUsername를 실행해서 만드는 Authentication의 password를 비교함. 그래서
     * AuthenticationProvider를 따로 구현할 필요없음.
     */
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);

    UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequestDto.getEmail());
    String token = jwtProvider.generateToken(userDetails);
    return ResponseEntity.ok().body(new LoginResponseDto(userDetails.getUsername(), token));
  }

  @GetMapping("/login")
  public String showLoginForm(Model model) {
    model.addAttribute("loginRequestDto", new LoginRequestDto());
    return "login";
  }
}
