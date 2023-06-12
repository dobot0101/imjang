package com.dobot.imjang.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class KakaoLoginController {
  private final KakaoLoginService kakaoLoginService;

  public KakaoLoginController(KakaoLoginService kakaoLoginService) {
    this.kakaoLoginService = kakaoLoginService;
  }

  @GetMapping("/kakao/callback")
  @ResponseBody
  public String kakaoCallback(@RequestParam("code") String code) {
    String result = this.kakaoLoginService.processCallback(code);
    return result;
  }
}