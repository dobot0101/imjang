package com.dobot.imjang.domain.member.dtos;

import java.util.UUID;

import lombok.Getter;

@Getter
public class CreateMemberResponse {
  UUID createdMemberId;

  public CreateMemberResponse(UUID createdMemberId) {
    this.createdMemberId = createdMemberId;
  }
}
