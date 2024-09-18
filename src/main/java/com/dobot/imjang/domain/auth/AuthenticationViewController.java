package com.dobot.imjang.domain.auth;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthenticationViewController {

  @GetMapping("/login")
  public String showLoginForm(Model model) {
    model.addAttribute("loginRequestDto", new LoginRequestDto());
    return "login";
  }

}
