package com.dobot.imjang.domain.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dobot.imjang.domain.member.dto.SignUpRequestDto;
import com.dobot.imjang.domain.member.service.MemberService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/api/members")
public class MemberController {
  private final MemberService memberService;

  public MemberController(MemberService memberService) {
    this.memberService = memberService;
  }

  @PostMapping("/signup")
  public String signup(@Valid SignUpRequestDto signUpRequestDto, BindingResult bindingResult) throws Exception {
    if (bindingResult.hasErrors()) {
      return "signup";
    }
    this.memberService.signUp(signUpRequestDto);
    return "redirect:/auth/login";
  }
}
