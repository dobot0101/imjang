package com.dobot.imjang.domain.auth.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dobot.imjang.domain.auth.dto.LoginRequestDto;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

  // SpringSecurity의 formLogin을 사용하게 돼서 아래 코드들 주석 처리
  // private final AuthService authService;
  // @PostMapping("/login")
  // public ResponseEntity<LoginResponseDto> login(@Valid LoginRequestDto
  // loginRequestDto)
  // throws Exception {
  // LoginResponseDto loginResponseDto =
  // authService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());
  // return ResponseEntity.ok().body(loginResponseDto);
  // }

  @GetMapping("/login")
  public String showLoginForm(Model model) {
    model.addAttribute("loginRequestDto", new LoginRequestDto());
    return "login";
  }
}
