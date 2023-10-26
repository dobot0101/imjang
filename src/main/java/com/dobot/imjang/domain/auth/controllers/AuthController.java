package com.dobot.imjang.domain.auth.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dobot.imjang.domain.auth.dtos.LoginRequestDto;
import com.dobot.imjang.domain.auth.dtos.LoginResponseDto;
import com.dobot.imjang.domain.auth.services.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

  private final AuthService authService;

  @PostMapping("/login")
  public ResponseEntity<LoginResponseDto> login(@Valid LoginRequestDto loginRequestDto,
      BindingResult bindingResult)
      throws Exception {
    if (bindingResult.hasErrors()) {
      StringBuilder errorMessage = new StringBuilder();
      for (FieldError error : bindingResult.getFieldErrors()) {
        errorMessage.append(error.getDefaultMessage()).append("; ");
      }
      LoginResponseDto loginResponseDto = LoginResponseDto.builder().errorMessage(errorMessage.toString()).build();
      return ResponseEntity.badRequest().body(loginResponseDto);
    }

    LoginResponseDto loginResponseDto = authService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());
    return ResponseEntity.ok().body(loginResponseDto);
  }

  @GetMapping("/login")
  public String showLoginForm(Model model) {
    model.addAttribute("loginRequestDto", new LoginRequestDto());
    return "login";
  }
}
