package com.dobot.imjang.domain.member.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dobot.imjang.domain.member.dtos.SignUpRequestDto;
import com.dobot.imjang.domain.member.services.MemberService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/member")
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

  @GetMapping("/signup")
  public String showSignupForm(Model model) {
    model.addAttribute("signUpRequestDto", new SignUpRequestDto());
    return "signup";
  }
}
