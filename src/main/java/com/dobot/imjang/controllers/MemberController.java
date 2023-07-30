package com.dobot.imjang.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dobot.imjang.dtos.CreateMemberResponse;
import com.dobot.imjang.dtos.MemberSignUpRequest;
import com.dobot.imjang.entities.Member;
import com.dobot.imjang.service.MemberService;

@RestController
@RequestMapping("/member")
public class MemberController {
  private final MemberService memberService;

  public MemberController(MemberService memberService) {
    this.memberService = memberService;
  }

  @PostMapping(path = "/signup")
  public ResponseEntity<CreateMemberResponse> signUp(@RequestBody @Validated MemberSignUpRequest memberSignUpRequest) {
    Member createdMember = this.memberService.createMember(memberSignUpRequest);
    CreateMemberResponse response = new CreateMemberResponse(createdMember.getId());
    return ResponseEntity.ok().body(response);
  }
}
