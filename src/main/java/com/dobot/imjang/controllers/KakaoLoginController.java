package com.dobot.imjang.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dobot.imjang.service.KakaoLoginService;

@RequestMapping(name = "/kakao-login")
@RestController
public class KakaoLoginController {
  private final KakaoLoginService kakaoLoginService;

  public KakaoLoginController(KakaoLoginService kakaoLoginService) {
    this.kakaoLoginService = kakaoLoginService;
  }

  @GetMapping("/callback")
  @ResponseBody
  public String callback(@RequestParam("code") String code) {
    return this.kakaoLoginService.processCallback(code);
  }
}