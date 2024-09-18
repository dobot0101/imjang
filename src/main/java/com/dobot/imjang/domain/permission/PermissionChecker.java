package com.dobot.imjang.domain.permission;

import java.util.UUID;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.dobot.imjang.domain.auth.CustomUserDetails;
import com.dobot.imjang.domain.common.exception.ValidationError;
import com.dobot.imjang.domain.common.exception.ErrorCode;
import com.dobot.imjang.domain.member.Member;
import com.dobot.imjang.domain.member.MemberRepository;
import com.dobot.imjang.domain.member.Role;

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
      throw new ValidationError(ErrorCode.PERMISSION_DENIED);
    }
  }

  public void checkAdminPermission(UUID memberId) {
    Member member = memberRepository.findById(memberId)
        .orElseThrow(() -> new ValidationError(ErrorCode.USER_NOT_FOUND));
    if (!member.getRole().equals(Role.ADMIN)) {
      throw new ValidationError(ErrorCode.PERMISSION_DENIED);
    }
  }
}
