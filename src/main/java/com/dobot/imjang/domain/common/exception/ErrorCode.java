package com.dobot.imjang.domain.common.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
  DUPLICATE_LOCATION(HttpStatus.BAD_REQUEST, "이미 존재하는 위치 정보입니다."),
  INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다."),
  BUILDING_NOT_FOUND(HttpStatus.NOT_FOUND, "빌딩 정보를 찾을 수 없습니다."),
  UNIT_NOT_FOUND(HttpStatus.NOT_FOUND, "유닛 정보를 찾을 수 없습니다."),
  MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "회원 정보를 찾을 수 없습니다."),
  JWT_NOT_VALID(HttpStatus.BAD_REQUEST, "유효하지 않은 토큰입니다.");

  private ErrorCode(HttpStatus status, String message) {
    this.status = status;
    this.message = message;
  };

  private HttpStatus status;

  public HttpStatus getStatus() {
    return status;
  }

  private String message;

  public String getMessage() {
    return message;
  }
}