package com.dobot.imjang.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dobot.imjang.dtos.MemberSignUpRequest;
import com.dobot.imjang.service.MemberService;

@RestController
@RequestMapping("/member")
public class MemberController {
  private final MemberService memberService;

  public MemberController(MemberService memberService) {
    this.memberService = memberService;
  }

  @PostMapping(path = "/signup")
  public ResponseEntity<String> signUp(@RequestBody @Validated MemberSignUpRequest memberSignUpRequest) {
    return ResponseEntity.ok().body(this.memberService.createMember(memberSignUpRequest));
  }
}
