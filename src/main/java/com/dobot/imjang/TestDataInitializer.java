package com.dobot.imjang;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.dobot.imjang.domain.member.dto.SignUpRequestDto;
import com.dobot.imjang.domain.member.service.MemberService;

@Component
public class TestDataInitializer implements CommandLineRunner {

  private final MemberService memberService;

  public TestDataInitializer(MemberService memberService) {
    this.memberService = memberService;
  }

  @Override
  public void run(String... args) throws Exception {
    SignUpRequestDto dto = SignUpRequestDto.builder().confirmPassword("test").password("test")
        .email("tester@imjang.com").name("테스터").build();
    memberService.signUp(dto);
  }
}
