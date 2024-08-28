package com.dobot.imjang.config;

import com.dobot.imjang.domain.auth.CustomUserDetails;
import com.dobot.imjang.domain.common.exception.CustomException;
import com.dobot.imjang.domain.common.exception.ErrorCode;
import com.dobot.imjang.domain.member.Member;
import com.dobot.imjang.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = this.memberRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        return new CustomUserDetails(member.getId(), member.getEmail(), member.getPassword(), null);
        
        // 필수 값 누락 방지를 위해 이 부분은 생성자를 사용하는게 낫다고 생각함
//        return CustomUserDetails.builder().id(member.getId()).username(member.getEmail())
//                .authorities(null).password(member.getPassword()).build();
    }
}
