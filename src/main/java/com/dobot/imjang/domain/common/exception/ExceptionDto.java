package com.dobot.imjang.domain.common.exception;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExceptionDto {
  private String message;
  private ErrorCode errorCode;

  public ErrorCode getErrorCode() {
    return errorCode;
  }

  private LocalDateTime timestamp;
  private Object errorData;

  @Builder
  public ExceptionDto(String message, ErrorCode errorCode, LocalDateTime timestamp, Object errorData) {
    this.message = message;
    this.errorCode = errorCode;
    this.timestamp = timestamp;
    this.errorData = errorData;
  }
}