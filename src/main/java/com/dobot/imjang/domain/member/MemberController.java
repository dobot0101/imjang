package com.dobot.imjang.domain.member;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {
  private final MemberService memberService;

  @PostMapping("/signup")
  public String signup(@Valid SignUpRequestDto signUpRequestDto, BindingResult bindingResult) throws Exception {
    if (bindingResult.hasErrors()) {
      return "signup";
    }
    this.memberService.signUp(signUpRequestDto);
    return "redirect:/auth/login";
  }
}
