package com.dobot.imjang;

import com.dobot.imjang.domain.member.MemberService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.dobot.imjang.domain.member.SignUpRequestDto;

@Component
public class TestDataInitializer implements CommandLineRunner {
    private final MemberService memberService;

    public TestDataInitializer(MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            SignUpRequestDto dto = SignUpRequestDto.builder().confirmPassword("test").password("test")
                    .email("tester@imjang.com").name("테스터").build();
            memberService.signUp(dto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
