package com.dobot.imjang.domain.member.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dobot.imjang.domain.auth.entity.CustomUserDetails;
import com.dobot.imjang.domain.member.dto.SignUpRequestDto;
import com.dobot.imjang.domain.member.service.MemberService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/members")
public class MemberViewController {
  private final MemberService memberService;

  public MemberViewController(MemberService memberService) {
    this.memberService = memberService;
  }

  @GetMapping("/mypage")
  public String showMyPage(Model model) {
    CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
        .getPrincipal();
    model.addAttribute("username", userDetails.getUsername());
    model.addAttribute("headerPageName", "마이페이지");
    return "mypage";
  }

  @GetMapping("/signup")
  public String showSignupForm(Model model) {
    model.addAttribute("signUpRequestDto", SignUpRequestDto.builder().build());
    model.addAttribute("headerPageName", "회원가입");
    return "signup";
  }

  @PostMapping("/signup_process")
  public String signup(@Valid SignUpRequestDto signUpRequestDto, BindingResult bindingResult) throws Exception {
    if (bindingResult.hasErrors()) {
      return "signup";
    }
    this.memberService.signUp(signUpRequestDto);
    return "redirect:/auth/login";
  }
}
