package com.dobot.imjang.domain.member.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dobot.imjang.domain.member.dtos.CreateMemberResponse;
import com.dobot.imjang.domain.member.dtos.MemberSignUpRequest;
import com.dobot.imjang.domain.member.entities.Member;
import com.dobot.imjang.domain.member.services.MemberService;

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
