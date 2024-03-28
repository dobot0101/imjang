package com.dobot.imjang;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.dobot.imjang.domain.member.MemberService;
import com.dobot.imjang.domain.member.SignUpRequestDto;

@Component
public class SystemInitializer implements CommandLineRunner {
    private final MemberService memberService;

    public SystemInitializer(MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            initAdminMember();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void initAdminMember() throws Exception {
        String password = "test";
        SignUpRequestDto dto = SignUpRequestDto.builder().confirmPassword(password).password(password)
                .email("tester@imjang.com").name("테스터").build();
        memberService.signUp(dto);
    }
}
