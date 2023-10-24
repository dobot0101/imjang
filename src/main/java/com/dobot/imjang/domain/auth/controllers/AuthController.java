package com.dobot.imjang.domain.auth.controllers;

import java.util.UUID;

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
import com.dobot.imjang.domain.member.entities.Member;
import com.dobot.imjang.domain.member.services.MemberService;
import com.dobot.imjang.util.JwtProvider;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {
  private final AuthService authService;
  private final JwtProvider jwtProvider;
  private final MemberService memberService;

  public AuthController(AuthService authService, JwtProvider jwtProvider, MemberService memberService) {
    this.authService = authService;
    this.jwtProvider = jwtProvider;
    this.memberService = memberService;
  }

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

    String token = this.authService.login(loginRequestDto);
    String memberId = this.jwtProvider.validateTokenAndGetSubject(token);
    Member member = this.memberService.getMemberById(UUID.fromString(memberId));
    
    return ResponseEntity.ok().body(new LoginResponseDto(member.getEmail(), member.getName(), token));
  }

  @GetMapping("/login")
  public String showLoginForm(Model model) {
    model.addAttribute("loginRequestDto", new LoginRequestDto());
    return "login";
  }
}
