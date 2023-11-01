package com.dobot.imjang.domain.permission;

import java.util.UUID;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.dobot.imjang.domain.auth.entity.CustomUserDetails;
import com.dobot.imjang.domain.common.exception.CustomException;
import com.dobot.imjang.domain.common.exception.ErrorCode;
import com.dobot.imjang.domain.member.entity.Member;
import com.dobot.imjang.domain.member.entity.Role;
import com.dobot.imjang.domain.member.repository.MemberRepository;

@Component
public class PermissionChecker {
  private final MemberRepository memberRepository;

  public PermissionChecker(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
  }

  public void checkPermission(UUID memberId) {
    CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
        .getPrincipal();
    if (!memberId.equals(customUserDetails.getId())) {
      throw new CustomException(ErrorCode.PERMISSION_DENIED);
    }
  }

  public void checkAdminPermission(UUID memberId) {
    Member member = memberRepository.findById(memberId)
        .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    if (!member.getRole().equals(Role.ADMIN)) {
      throw new CustomException(ErrorCode.PERMISSION_DENIED);
    }
  }
}
